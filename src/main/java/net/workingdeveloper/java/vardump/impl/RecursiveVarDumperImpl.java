package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.IVarDumper;
import net.workingdeveloper.java.vardump.IVarDumperCyclicRegistry;
import net.workingdeveloper.java.vardump.IVarDumperFormatter;
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
    private Logger logger = LoggerFactory.getLogger(VarDumperFormatterImpl.class);

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
        setFormatter(aFormatter);
        reflectionSwitch(aObject);
        return getFormatter().toString();
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

    protected void reflectionSwitch(final Object aObject) {
        IVarDumperFormatter lFormatter = getFormatter();
        if (null == aObject) {
            lFormatter.appendNull();
            return;
        }

        final Class<?> clazz = aObject.getClass();
        if (isPrimitiveType(aObject)) {
            lFormatter.appendPrimitiveFieldValue(aObject);
            return;
        }
//        lFormatter.appendStartDump(aObject);

        if (isArrayType(aObject)) {
            reflectArray(aObject);
        } else if (isCollectionType(aObject)) {
            reflectCollection(aObject);
        } else if (isMapType(aObject)) {
            reflectMap(aObject);
        } else {

            reflectObjectField(aObject, clazz);
        }
    }

    private void reflectMap(Object aObject) {
        Map<?, ?>                      lMapObject = (Map<?, ?>) aObject;
        Set<? extends Map.Entry<?, ?>> lEntrySet  = lMapObject.entrySet();
        IVarDumperFormatter            lFormatter = getFormatter();
        lFormatter.openMap(aObject);
        for (Map.Entry<?, ?> lEntry : lEntrySet) {
            String lFieldName = lEntry.getKey().toString();
            lFormatter.openField(lFieldName);
            reflectionSwitch(lEntry.getValue());
            lFormatter.closeField(lFieldName);
        }
        lFormatter.closeMap(aObject);
    }

    private boolean isMapType(Object aObject) {
        return aObject instanceof Map;
    }

    private void reflectCollection(Object aObject) {
        IVarDumperFormatter lFormatter = getFormatter();
        Collection<?>       lArray     = (Collection<?>) aObject;
        lFormatter.openArray(aObject);
        for (Object lO : lArray) {
            reflectionSwitch(lO);
        }
        lFormatter.closeArray(aObject);
    }

    private void appendFieldsIn(final Object aObject, final Class<?> aClazz) {
        IVarDumperFormatter lFormatter = getFormatter();
        final Field[]       fields     = aClazz.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (final Field field : fields) {
            final String fieldName = field.getName();
            if (accept(field)) {
                try {
                    lFormatter.openField(fieldName);
                    Object lFieldValue = field.get(aObject);
                    if (null == lFieldValue) {
                        lFormatter.appendNull();
                    } else if (getRegistry().isRegistered(lFieldValue)) {
                        lFormatter.appendFieldValueReference(lFieldValue);
                    } else {
                        getRegistry().register(lFieldValue);
                        RecursiveVarDumperImpl lSubDumper = new RecursiveVarDumperImpl(
                                getFormatter(), getFieldAcceptor(),
                                getRegistry()
                        );
                        lSubDumper.varDumpSub(lFieldValue);
                    }
                    lFormatter.closeField(fieldName);
                } catch (IllegalAccessException aE) {
                    logger.error(MarkerFactory.getMarker("EXCEPTION"), aE.getLocalizedMessage(), aE);
                }
            }
        }
    }

    private void reflectObjectField(final Object aObject, final Class<?> aClazz) {
        IVarDumperFormatter lFormatter = getFormatter();
        getRegistry().register(aObject);
        lFormatter.openObject(aObject);
        Class<?> lClazz = aClazz;
        appendFieldsIn(aObject, lClazz);
        while (lClazz.getSuperclass() != null && lClazz != this.getUpToClass()) {
            lClazz = lClazz.getSuperclass();
            appendFieldsIn(aObject, lClazz);
        }

        lFormatter.closeObject(aObject);
    }

    private void varDumpSub(final Object aObject) {
        reflectionSwitch(aObject);
    }

    private void reflectArray(final Object aObject) {
        IVarDumperFormatter lFormatter = getFormatter();
        Object[]            lArray     = (Object[]) aObject;
        lFormatter.openArray(aObject);
        for (Object lO : lArray) {
            reflectionSwitch(lO);
        }
        lFormatter.closeArray(aObject);
    }

    private boolean isArrayType(final Object aObject) {
        return aObject.getClass().isArray();
    }

    private boolean isCollectionType(final Object aObject) {
        return aObject instanceof Collection;
    }

    private boolean isPrimitiveType(final Object aObject) {
        Class<?> lClass = aObject.getClass();
        return lClass.isEnum()
                || lClass.isPrimitive()
                || aObject instanceof CharSequence
                || aObject instanceof Number
                || aObject instanceof Boolean
                || aObject instanceof Character;
    }
}
