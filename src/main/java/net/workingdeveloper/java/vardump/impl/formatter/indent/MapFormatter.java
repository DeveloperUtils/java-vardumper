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
class MapFormatter extends ElementFormatter<IElementFormatter> implements IMapFormatter {

    private int fEntryCount = 0;

    MapFormatter(int aIndention, IElementFormatter aParent, Appendable aBuffer, FormatterFactory aFactory) {
        super(aIndention, aParent, aBuffer, aFactory);
    }

    @Override
    public <T> ElementFormatter open(T aMap) {
        append(getObjectName(aMap, true));
        append("{");
        return this;
    }

    @Override
    public IMapEntryFormatter openEntry(Map.Entry<?, ?> aObject) {
        final IMapEntryFormatter lMapEntryFormatter = fFactory.createMapEntryFormatter(
                aObject, fBuffer, fEntryCount, getIndentionLevel() + 1, this);
        fEntryCount++;
        return lMapEntryFormatter;
    }

    @Override
    protected void appendClosing() {
        append("\n").appendIndention().append("}");
    }
}
