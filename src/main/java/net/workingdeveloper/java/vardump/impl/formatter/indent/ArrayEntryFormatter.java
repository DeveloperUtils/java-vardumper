package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.IArrayEntryFormatter;

/**
 * Created by Christoph Graupner on 2017-01-03.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class ArrayEntryFormatter extends ContainerElementFormatter<ArrayFormatter> implements IArrayEntryFormatter {
    private final int fEntryNumber;

    public ArrayEntryFormatter(ArrayFormatter aParent, Appendable aBuffer, FormatterFactory aFactory, int aEntryNumber) {
        super(aParent, aBuffer, aFactory);
        fEntryNumber = aEntryNumber;
    }

    @Override
    public <T> ElementFormatter open(T aObject) {
        if (fEntryNumber > 0)
            append(",");
        return this;
    }

    @Override
    protected void appendClosing() {

    }
}
