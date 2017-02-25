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

    MapEntryFormatter(int aIndention, IMapFormatter aParent, Appendable aBuffer, FormatterFactory aFactory) {
        super(aIndention, aParent, aBuffer, aFactory);
    }

    @Override
    public ElementFormatter open(Object aObject) {
        Map.Entry aEntry     = (Map.Entry) aObject;
        String    lFieldName = aEntry.getKey().toString();
        append(lFieldName).append(": ");
        return this;
    }

    @Override
    protected void appendClosing() {
        append(",");
    }
}
