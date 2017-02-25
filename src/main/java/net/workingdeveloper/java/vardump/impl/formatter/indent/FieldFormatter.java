package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.IElementFormatter;
import net.workingdeveloper.java.vardump.formatter.IFieldFormatter;

/**
 * Created by Christoph Graupner on 2017-01-14.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
class FieldFormatter extends ContainerElementFormatter<IElementFormatter> implements IFieldFormatter {

    FieldFormatter(int aIndention, IElementFormatter aParent, Appendable aBuffer, FormatterFactory aFactory) {
        super(aIndention, aParent, aBuffer, aFactory);
    }

    @Override
    public <T> ElementFormatter open(T aFieldName) {
        appendIndention().append((CharSequence) aFieldName).append(" = ");
        return this;
    }

    @Override
    protected void appendClosing() {
        append(";");
    }
}
