package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.IArrayEntryFormatter;
import net.workingdeveloper.java.vardump.formatter.IArrayFormatter;

/**
 * Created by Christoph Graupner on 2017-01-03.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
class ArrayEntryFormatter extends ContainerElementFormatter<IArrayFormatter> implements IArrayEntryFormatter {
    private final int fEntryNumber;

    public ArrayEntryFormatter(int aIndention, IArrayFormatter aParent, Appendable aBuffer, FormatterFactory aFactory, int aEntryNumber) {
        super(aIndention, aParent, aBuffer, aFactory);
        fEntryNumber = aEntryNumber;
    }

    @Override
    public <T> ElementFormatter open(T aObject) {
        if (fEntryNumber > 0) {
            append(",");
        }
        append("\n").appendIndention();
        return this;
    }

    @Override
    protected void appendClosing() {

    }
}
