package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.IElementFormatter;
import net.workingdeveloper.java.vardump.formatter.IFieldFormatter;
import net.workingdeveloper.java.vardump.formatter.IObjectFormatter;

/**
 * Created by Christoph Graupner on 2017-01-02.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class ObjectFormatter extends ElementFormatter<IElementFormatter> implements IObjectFormatter {
    ObjectFormatter(IElementFormatter aParent, Appendable aBuffer, FormatterFactory aFactory) {
        super(aParent, aBuffer, aFactory);
    }

    ObjectFormatter(Appendable aBuffer, FormatterFactory aFactory) {
        super(aBuffer, aFactory);
    }

    @Override
    public <T> ElementFormatter open(T aObject) {
        append(getObjectName(aObject, fFactory.isShortClassName()));
        append("{");
        return this;
    }

    @Override
    public IFieldFormatter openField(String aName) {
        return fFactory.createFieldFormatter(aName, fBuffer);
    }

    @Override
    protected void appendClosing() {
        append("}");
    }
}
