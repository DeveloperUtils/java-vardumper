package net.workingdeveloper.java.vardump.impl.formatter;

import net.workingdeveloper.java.vardump.formatter.*;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Christoph Graupner on 2017-01-02.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IVarDumperFormatterFactory {
    IArrayEntryFormatter createArrayEntryFormatter(Object aObject, Appendable aBuffer, int aEntryNumber, int aIndention, IArrayFormatter aParent);

    IArrayFormatter createArrayFormatter(Collection<?> aCollection, Appendable aBuffer, int aIndentation, IElementFormatter aParent);

    IArrayFormatter createArrayFormatter(Object[] aArray, Appendable aBuffer, int aIndentation, IElementFormatter aParent);

    IMapEntryFormatter createMapEntryFormatter(Map.Entry<?, ?> aEntry, Appendable aBuffer, int aEntryNumber, int aIndentation, IMapFormatter aParent);

    IMapFormatter createMapFormatter(Map<?, ?> aMap, Appendable aBuffer, int aIndentation, IElementFormatter aParent);

    IObjectFormatter createObjectFormatter(Object aObject, Appendable aBuffer, int aIndentation, IElementFormatter aParent);

    IPrimitiveFormatter createPrimitiveFormatter(Appendable aBuffer, int aIndentation, IElementFormatter aParent);

    boolean isShortClassName();

    void setShortClassName(boolean aShortClassName);
}
