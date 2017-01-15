package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.IVarDumper;
import net.workingdeveloper.java.vardump.IVarDumperCyclicRegistry;
import net.workingdeveloper.java.vardump.IVarDumperFormatter;
import net.workingdeveloper.java.vardump.formatter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by Christoph Graupner on 2016-10-17.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class RecursiveVarDumperImpl implements IVarDumper {
    Predicate<Field>         fFieldAcceptor;
    IVarDumperCyclicRegistry fRegistry;
    private String[]            excludeFieldNames;
    private boolean             fAppendStatics;
    private boolean             fAppendTransients;
    private IVarDumperFormatter fFormatter;
    private Class<?>            fUpToClass;
    private Logger logger = LoggerFactory.getLogger(RecursiveVarDumperImpl.class);

    public RecursiveVarDumperImpl(final IVarDumperFormatter aFormatter, final Predicate<Field> aFieldPredicate, final IVarDumperCyclicRegistry aRegistry) {
        fFormatter = aFormatter;
        fRegistry = aRegistry;
        fFieldAcceptor = aFieldPredicate;
    }

    public Predicate<Field> getFieldAcceptor() {
        return fFieldAcceptor;
    }

    public IVarDumperFormatter getFormatter() {
        return fFormatter;
    }

    public void setFormatter(final IVarDumperFormatter aFormatter) {
        fFormatter = aFormatter;
    }

    public IVarDumperCyclicRegistry getRegistry() {
        return fRegistry;
    }

    public Class<?> getUpToClass() {
        return fUpToClass;
    }

    public boolean isAppendStatics() {
        return fAppendStatics;
    }

    public boolean isAppendTransients() {
        return fAppendTransients;
    }

    @Override
    public String vardump(final Object aObject, final IVarDumperFormatter aFormatter) {
        reset();
        setFormatter(aFormatter);
        final IVarDumperFormatter lFormatter = getFormatter();
        lFormatter.startDump();
        reflectionSwitch(aObject, lFormatter);
        lFormatter.endDump();
        return lFormatter.toString();
    }

    @Override
    public String vardump(final Object aObject) {
        return vardump(aObject, getFormatter());
    }

    /**
     * Returns whether or not to append the given <code>Field</code>.
     * <ul>
     * <li>Transient fields are appended only if {@link #isAppendTransients()} returns <code>true</code>.
     * <li>Static fields are appended only if {@link #isAppendStatics()} returns <code>true</code>.
     * <li>Inner class fields are not appended.</li>
     * </ul>
     *
     * @param field The Field to test.
     *
     * @return Whether or not to append the given <code>Field</code>.
     */
    protected boolean accept(final Field field) {
        if (fFieldAcceptor != null) {
            return fFieldAcceptor.test(field);
        }
        return true;
    }

    protected void reflectionSwitch(final Object aObject, final IContainerElementFormatter lFormatter) {
        if (null == aObject) {
            lFormatter.appendNull();
            return;
        }

        final Class<?> clazz = aObject.getClass();
        if (isPrimitiveType(aObject)) {
            lFormatter.openPrimitive()
                      .appendPrimitive(aObject)
                      .close();
        } else if (isArrayType(aObject)) {
            final IArrayFormatter lArrayFormatter = lFormatter.openArray((Object[]) aObject);
            reflectArray(aObject, lArrayFormatter);
            lArrayFormatter.close();
        } else if (isCollectionType(aObject)) {
            final IArrayFormatter lArrayFormatter = lFormatter.openArray((Collection<?>) aObject);
            reflectCollection(aObject, lArrayFormatter);
            lArrayFormatter.close();
        } else if (isMapType(aObject)) {
            final IMapFormatter lMapFormatter = lFormatter.openMap((Map<?, ?>) aObject);
            reflectMap(aObject, lMapFormatter);
            lMapFormatter.close();
        } else {
            final IObjectFormatter lObjectFormatter = lFormatter.openObject(aObject);
            reflectObject(aObject, clazz, lObjectFormatter);
            lObjectFormatter.close();
        }
    }

    private void reset() {
        getFormatter().reset();
    }

    private void reflectMap(Object aObject, IMapFormatter aFormatter) {
        Map<?, ?>                      lMapObject = (Map<?, ?>) aObject;
        Set<? extends Map.Entry<?, ?>> lEntrySet  = lMapObject.entrySet();

        for (Map.Entry<?, ?> lEntry : lEntrySet) {
            IMapEntryFormatter lEntryFormatter = aFormatter.openEntry(lEntry);
            reflectionSwitch(lEntry.getValue(), lEntryFormatter);
            lEntryFormatter.close();
        }
    }

    private boolean isMapType(Object aObject) {
        return aObject instanceof Map;
    }

    private void reflectCollection(Object aObject, IArrayFormatter aLFormatter) {
        Collection<?> lArray = (Collection<?>) aObject;
        for (Object lO : lArray) {
            IArrayEntryFormatter lEntryFormatter = aLFormatter.openEntry(lO);
            reflectionSwitch(lO, lEntryFormatter);
            lEntryFormatter.close();
        }
    }

    private void appendFieldsIn(final Object aObject, final Class<?> aClazz, IObjectFormatter aObjectFormatter) {
        final Field[] fields = aClazz.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (final Field field : fields) {
            final String fieldName = field.getName();
            if (accept(field)) {

                try {
                    IFieldFormatter lFieldFormatter = aObjectFormatter.openField(fieldName);
                    if (isPrimitiveType(field)) {
                        IPrimitiveFormatter lPrimitiveFormatter = lFieldFormatter.openPrimitive();
                        lPrimitiveFormatter.appendPrimitive(aObject, field);
                    } else {
                        Object lFieldValue = field.get(aObject);
                        if (null == lFieldValue) {
                            lFieldFormatter.appendNull();
                        } else if (getRegistry().isRegistered(lFieldValue)) {
                            lFieldFormatter.appendFieldValueReference(lFieldValue);
                        } else {
                            getRegistry().register(lFieldValue);
                            RecursiveVarDumperImpl lSubDumper = new RecursiveVarDumperImpl(
                                    getFormatter(), getFieldAcceptor(),
                                    getRegistry()
                            );
                            IObjectFormatter lObjectFormatter = lFieldFormatter.openObject(lFieldValue);
                            lSubDumper.reflectObject(
                                    lFieldValue,
                                    aObject.getClass(),
                                    lObjectFormatter
                            );
                            lObjectFormatter.close();
                        }
                    }
                    lFieldFormatter.close();
                } catch (IllegalAccessException aE) {
                    logger.error(MarkerFactory.getMarker("EXCEPTION"), aE.getLocalizedMessage(), aE);
                }
            }
        }
    }

    private void reflectObject(final Object aObject, final Class<?> aClazz, IObjectFormatter aLFormatter) {
        getRegistry().register(aObject);
        Class<?> lClazz = aClazz;
        appendFieldsIn(aObject, lClazz, aLFormatter);
        while (lClazz.getSuperclass() != null && lClazz != this.getUpToClass()) {
            lClazz = lClazz.getSuperclass();
            appendFieldsIn(aObject, lClazz, aLFormatter);
        }
    }

    private void reflectArray(final Object aObject, IArrayFormatter aLFormatter) {
        Object[] lArray = (Object[]) aObject;
        for (Object lO : lArray) {
            IArrayEntryFormatter lEntryFormatter = aLFormatter.openEntry(lO);
            reflectionSwitch(lO, lEntryFormatter);
            lEntryFormatter.close();
        }
    }

    private boolean isArrayType(final Object aObject) {
        return aObject.getClass().isArray();
    }

    private boolean isCollectionType(final Object aObject) {
        return aObject instanceof Collection;
    }

    private boolean isPrimitiveType(final Field aObject) {
        return DumperUtils.isPrimitiveType(aObject);
    }

    private boolean isPrimitiveType(final Object aObject) {
        return DumperUtils.isPrimitiveType(aObject);
    }
}
