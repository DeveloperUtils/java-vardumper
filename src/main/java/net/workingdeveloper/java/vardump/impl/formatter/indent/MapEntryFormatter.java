package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.IMapEntryFormatter;
import net.workingdeveloper.java.vardump.formatter.IMapFormatter;

import java.util.Map;

/**
 * Created by Christoph Graupner on 2017-01-03.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
class MapEntryFormatter extends ContainerElementFormatter<IMapFormatter> implements IMapEntryFormatter {

    private final int fEntryNumber;

    MapEntryFormatter(int aIndention, IMapFormatter aParent, Appendable aBuffer, FormatterFactory aFactory, int aEntryNumber) {
        super(aIndention, aParent, aBuffer, aFactory);
        fEntryNumber = aEntryNumber;
    }

    @Override
    public ElementFormatter open(Object aObject) {
        if (fEntryNumber > 0) {
            append(",");
        }
        append("\n").appendIndention();
        Map.Entry aEntry     = (Map.Entry) aObject;
        String    lFieldName = aEntry.getKey().toString();
        append(lFieldName).append(": ");
        return this;
    }

    @Override
    protected void appendClosing() {

    }
}
