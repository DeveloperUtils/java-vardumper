package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.IElementFormatter;
import net.workingdeveloper.java.vardump.formatter.IFieldFormatter;
import net.workingdeveloper.java.vardump.formatter.IObjectFormatter;

/**
 * Created by Christoph Graupner on 2017-01-02.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
class ObjectFormatter extends ElementFormatter<IElementFormatter> implements IObjectFormatter {

    private Object fObject;

    ObjectFormatter(int aIndention, IElementFormatter aParent, Appendable aBuffer, FormatterFactory aFactory) {
        super(aIndention, aParent, aBuffer, aFactory);
    }

    @Override
    public <T> ElementFormatter open(T aObject) {
        fObject = aObject;
        append(getObjectName(aObject, fFactory.isShortClassName()));
        append(getLiteral(Literals.OPEN_OBJECT));
        return this;
    }

    @Override
    public IFieldFormatter openField(String aName) {
        append(getLiteral(Literals.NEW_LINE));
        return fFactory.createFieldFormatter(aName, fBuffer, getIndentionLevel() + 1, this);
    }

    @Override
    protected void appendClosing() {
        append(getLiteral(Literals.NEW_LINE))
                .appendIndention()
                .append(getLiteral(Literals.CLOSE_OBJECT))
                .append(" ").append(getLiteral(Literals.LINE_COMMENT))
                .append(getObjectName(fObject, fFactory.isShortClassName()));
    }
}
