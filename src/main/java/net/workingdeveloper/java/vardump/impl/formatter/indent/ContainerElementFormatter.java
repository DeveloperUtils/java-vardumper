package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.*;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Christoph Graupner on 2017-01-02.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public abstract class ContainerElementFormatter<PARENT extends IElementFormatter> extends ElementFormatter<PARENT> implements IContainerElementFormatter {
    public ContainerElementFormatter(PARENT aParent, Appendable aBuffer, FormatterFactory aFactory) {
        super(aParent, aBuffer, aFactory);
    }

    @Override
    public IArrayFormatter openArray(Object[] aObjects) {
        return fFactory.createArrayFormatter(aObjects,fBuffer);
    }

    @Override
    public IArrayFormatter openArray(Collection<?> aCollection) {
        return fFactory.createArrayFormatter(aCollection, fBuffer);
    }

    @Override
    public IMapFormatter openMap(Map<?, ?> aMap) {
        return fFactory.createMapFormatter(aMap,fBuffer);
    }

    @Override
    public IObjectFormatter openObject(Object aObject) {
        return fFactory.createObjectFormatter(aObject,fBuffer);
    }

    @Override
    public IPrimitiveFormatter openPrimitive() {
        return fFactory.createPrimitiveFormatter(fBuffer);
    }
}
