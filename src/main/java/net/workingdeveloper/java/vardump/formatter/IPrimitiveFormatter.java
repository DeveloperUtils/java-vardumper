package net.workingdeveloper.java.vardump.formatter;

import java.lang.reflect.Field;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IPrimitiveFormatter extends IElementFormatter {

    IPrimitiveFormatter appendPrimitive(Object aObject);
    IPrimitiveFormatter appendPrimitive(Object aParentObject, Field aField) throws IllegalAccessException;
}
