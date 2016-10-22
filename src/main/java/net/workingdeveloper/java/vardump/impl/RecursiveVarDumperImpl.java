package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.IVarDumper;
import net.workingdeveloper.java.vardump.IVarDumperCyclicRegistry;
import net.workingdeveloper.java.vardump.IVarDumperFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

/**
 * Created by Christoph Graupner on 2016-10-17.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class RecursiveVarDumperImpl implements IVarDumper {
    IVarDumperCyclicRegistry fRegistry;
    private IVarDumperFormatter fFormatter;
    private Logger logger = LoggerFactory.getLogger(VarDumperFormatterImpl.class);

    public RecursiveVarDumperImpl(IVarDumperFormatter aFormatter, IVarDumperCyclicRegistry aRegistry) {
        fFormatter = aFormatter;
        fRegistry = aRegistry;
    }

    public IVarDumperFormatter getFormatter() {
        return fFormatter;
    }

    public void setFormatter(IVarDumperFormatter aFormatter) {
        fFormatter = aFormatter;
    }

    public IVarDumperCyclicRegistry getRegistry() {
        return fRegistry;
    }

    @Override
    public String vardump(Object aObject) {
        return vardump(aObject, getFormatter());
    }

    @Override
    public String vardump(Object aObject, IVarDumperFormatter aFormatter) {
        setFormatter(aFormatter);
        reflectMainObject(aObject);
        return getFormatter().toString();
    }

    protected void reflectMainObject(final Object aObject) {
        IVarDumperFormatter lFormatter = getFormatter();
        if (null == aObject) {
            lFormatter.appendNull();
            return;
        }

        final Class<?> clazz = aObject.getClass();
        if (isPrimitiveType(aObject)) {
            lFormatter.appendFieldValue(aObject);
            return;
        }
        lFormatter.appendStartDump(aObject.getClass().getCanonicalName(), aObject);
        if (isArrayType(aObject)) {
            reflectArray(aObject);
        } else {
            reflectObjectField(aObject, lFormatter, clazz);
        }
    }

    private void reflectObjectField(Object aObject, IVarDumperFormatter aFormatter, Class<?> aClazz) {
        aFormatter.openObject(aClazz.getCanonicalName());
        final Field[] fields = aClazz.getDeclaredFields();
        AccessibleObject.setAccessible(fields, true);
        for (final Field field : fields) {
            final String fieldName = field.getName();
            aFormatter.openField(fieldName);
            try {
                Object lFieldValue = field.get(aObject);
                if (getRegistry().isRegistered(lFieldValue)) {
                    aFormatter.appendFieldValueReference(aObject);
                } else {
                    getRegistry().register(aObject);
                    RecursiveVarDumperImpl lSubDumper = new RecursiveVarDumperImpl(getFormatter(), getRegistry());
                    lSubDumper.varDumpSub(lFieldValue);
                }
                aFormatter.closeField(fieldName);
            } catch (IllegalAccessException aE) {
                logger.error(MarkerFactory.getMarker("EXCEPTION"), aE.getLocalizedMessage(), aE);
            }
        }
        aFormatter.closeObject(aObject.getClass().getCanonicalName());
    }

    private void varDumpSub(Object aObject) {
        reflectMainObject(aObject);
    }

    private void reflectArray(Object aObject) {
        IVarDumperFormatter lFormatter = getFormatter();
        lFormatter.openArray(aObject);

        lFormatter.closeArray(aObject);
    }

    private boolean isArrayType(Object aObject) {
        return aObject.getClass().isArray();
    }

    private boolean isPrimitiveType(Object aObject) {
        if (aObject instanceof Number
                || aObject instanceof String) {
            return true;
        }
        return false;
    }
}
