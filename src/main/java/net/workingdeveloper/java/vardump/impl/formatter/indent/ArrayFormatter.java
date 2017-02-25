package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.IArrayEntryFormatter;
import net.workingdeveloper.java.vardump.formatter.IArrayFormatter;
import net.workingdeveloper.java.vardump.formatter.IElementFormatter;

import java.util.Collection;

/**
 * Created by Christoph Graupner on 2017-01-02.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class ArrayFormatter extends ElementFormatter<IElementFormatter> implements IArrayFormatter {

    private int fEntryCount = 0;

    ArrayFormatter(int aIndention, IElementFormatter aParent, Appendable aBuffer, FormatterFactory aFactory) {
        super(aIndention, aParent, aBuffer, aFactory);
    }

    @Override
    public <T> ElementFormatter open(T aArray) {
        append(getObjectName(aArray, fFactory.isShortClassName()));
        int lSize = -1;
        if (aArray.getClass().isArray()) {
            lSize = ((Object[]) aArray).length;
        } else if (aArray instanceof Collection) {
            lSize = ((Collection) aArray).size();
        }
        append("(").append(Integer.toString(lSize)).append(")");
        append("[");

        return this;
    }

    @Override
    public IArrayEntryFormatter openEntry(Object aObject) {
        final IArrayEntryFormatter lArrayEntryFormatter = fFactory.createArrayEntryFormatter(
                aObject, fBuffer, fEntryCount, getIndentionLevel()+1, this);
        fEntryCount++;
        return lArrayEntryFormatter;
    }

    @Override
    protected void appendClosing() {
        append("\n").appendIndention().append("]");
    }
}
