package net.workingdeveloper.java.vardump.formatter;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Christoph Graupner on 2017-01-02.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IContainerElementFormatter extends IElementFormatter {
    IArrayFormatter openArray(Object[] aObjects);

    IArrayFormatter openArray(Collection<?> aCollection);

    IMapFormatter openMap(Map<?, ?> aMap);

    IObjectFormatter openObject(Object aObject);

    IPrimitiveFormatter openPrimitive();
}
