package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.IElementFormatter;
import net.workingdeveloper.java.vardump.formatter.IMapEntryFormatter;
import net.workingdeveloper.java.vardump.formatter.IMapFormatter;

import java.util.Map;

/**
 * Created by Christoph Graupner on 2017-01-02.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class MapFormatter extends ElementFormatter<IElementFormatter> implements IMapFormatter {
    public MapFormatter(IElementFormatter aParent, Appendable aBuffer, FormatterFactory aFactory) {
        super(aParent, aBuffer, aFactory);
    }

    public MapFormatter(Appendable aBuffer, FormatterFactory aFactory) {
        super(aBuffer, aFactory);
    }

    @Override
    protected void appendClosing() {
        append("}");
    }

    @Override
    public <T> ElementFormatter open(T aMap) {
        append(getObjectName(aMap, true));
        append(" {");
        return this;
    }

    @Override
    public IMapEntryFormatter openEntry(Map.Entry<?, ?> aObject) {
        return fFactory.createMapEntryFormatter(aObject, fBuffer);
    }
}
