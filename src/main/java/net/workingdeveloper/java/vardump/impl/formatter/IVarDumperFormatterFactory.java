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
    IArrayEntryFormatter createArrayEntryFormatter(Object aObject, Appendable aBuffer);

    IArrayFormatter createArrayFormatter(Collection<?> aCollection, Appendable aBuffer);

    IArrayFormatter createArrayFormatter(Object[] aArray, Appendable aBuffer);

    IMapEntryFormatter createMapEntryFormatter(Map.Entry<?, ?> aEntry, Appendable aBuffer);

    IMapFormatter createMapFormatter(Map<?, ?> aMap, Appendable aBuffer);

    IObjectFormatter createObjectFormatter(Object aObject, Appendable aBuffer);

    IPrimitiveFormatter createPrimitiveFormatter(Appendable aBuffer);

    boolean isShortClassName();

    void setShortClassName(boolean aShortClassName);
}
