package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.IElementFormatter;
import net.workingdeveloper.java.vardump.formatter.IPrimitiveFormatter;
import net.workingdeveloper.java.vardump.impl.DumperUtils;

import java.lang.reflect.Field;

/**
 * Created by Christoph Graupner on 2017-01-02.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class PrimitiveFormatter extends ElementFormatter<IElementFormatter> implements IPrimitiveFormatter {
    PrimitiveFormatter(IElementFormatter aParent, Appendable aBuffer, FormatterFactory aFactory) {
        super(aParent, aBuffer, aFactory);
    }

    PrimitiveFormatter(Appendable aBuffer, FormatterFactory aFactory) {
        super(aBuffer, aFactory);
    }

    @Override
    public IPrimitiveFormatter appendPrimitive(Object aObject) {
        if (aObject == null) {
            appendNull();
            return this;
        }
        append('(').append(aObject.getClass().getSimpleName()).append(")");

        if (aObject instanceof CharSequence) {
            append('"').append(aObject.toString()).append('"');
        } else if (aObject instanceof Character) {
            append('\'').append(aObject.toString()).append('\'');
        } else {
            append(aObject.toString());
        }
        return this;
    }

    @Override
    public IPrimitiveFormatter appendPrimitive(Object aParentObject, Field aField) throws IllegalAccessException {
        Class<?> lType = aField.getType();
        if (lType.isPrimitive()) {
            switch (lType.getName()) {
                case "boolean":
                    append("(boolean)").append(String.valueOf(aField.getBoolean(aParentObject)));
                    break;
                case "double":
                    append("(double)").append(String.valueOf(aField.getDouble(aParentObject)));
                    break;
                case "float":
                    append("(float)").append(String.valueOf(aField.getFloat(aParentObject)));
                    break;
                case "int":
                    append("(int)").append(String.valueOf(aField.getInt(aParentObject)));
                    break;
                case "long":
                    append("(long)").append(String.valueOf(aField.getLong(aParentObject)));
                    break;
                case "short":
                    append("(short)").append(String.valueOf(aField.getShort(aParentObject)));
                    break;
                case "byte":
                    append("(byte)").append(String.valueOf(aField.getByte(aParentObject)));
                    break;
                case "char":
                    append("(char)").append('\'').append(String.valueOf(aField.getChar(aParentObject))).append('\'');
                    break;
                default:
                    append("(UNKNOWN:" + lType.getName() + ")").append(String.valueOf(aField.getChar(aParentObject)));
                    break;
            }
        } else if (DumperUtils.isPrimitiveType(lType)) {
            Object lValue = aField.get(aParentObject);
            if (null == lValue) {
                append('(').append(lType.getSimpleName()).append(")")
                           .appendNull();
            } else {
                appendPrimitive(lValue);
            }
        } else {
            throw new IllegalArgumentException(aField.getName() + " is not a primitive type field.");
        }
        return this;
    }

    @Override
    public <T> ElementFormatter open(T aObject) {
        appendPrimitive(aObject);
        return this;
    }

    @Override
    protected void appendClosing() {

    }
}
