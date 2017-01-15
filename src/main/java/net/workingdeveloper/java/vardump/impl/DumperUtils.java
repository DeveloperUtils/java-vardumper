package net.workingdeveloper.java.vardump.impl;

import java.lang.reflect.Field;

/**
 * Created by Christoph Graupner on 2017-01-15.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class DumperUtils {
    public static boolean isPrimitiveType(final Field aObject) {
        Class<?> lType = aObject.getType();
        return isPrimitiveType(lType);
    }

    public static boolean isPrimitiveType(final Class lType) {
        return lType.isPrimitive()
                || lType.isEnum()
                || CharSequence.class.isAssignableFrom(lType)
                || Number.class.isAssignableFrom(lType)
                || Boolean.class.isAssignableFrom(lType)
                || Character.class.isAssignableFrom(lType);
    }

    public static boolean isPrimitiveType(final Object aObject) {
        Class<?> lClass = aObject.getClass();
        return isPrimitiveType(lClass);
    }
}
