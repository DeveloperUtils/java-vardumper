package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.formatter.IElementFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.io.Flushable;
import java.io.IOException;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public abstract class ElementFormatter<PARENT extends IElementFormatter> implements IElementFormatter {
    final FormatterFactory fFactory;
    final Logger logger = LoggerFactory.getLogger(getClass());
    Appendable fBuffer;
    int        fIndentation;
    PARENT     fParent;

    ElementFormatter(int aIndention, PARENT aParent, Appendable aBuffer, FormatterFactory aFactory) {
        fIndentation = aIndention;
        fParent = aParent;
        fBuffer = aBuffer;
        fFactory = aFactory;
    }

    ElementFormatter(int aIndention, Appendable aBuffer, FormatterFactory aFactory) {
        this(aIndention, null, aBuffer, aFactory);
    }

    @Override
    public <FLUENT extends IElementFormatter> FLUENT appendComment(String aComment) {
        return null;
    }

    @Override
    public <FLUENT extends IElementFormatter> FLUENT appendFieldValueReference(Object aObject) {
        append(fFactory.getRefString()).append(getObjectName(aObject, false));
        return (FLUENT) this;
    }

    @Override
    public <FLUENT extends IElementFormatter> FLUENT appendNull() {
        append(fFactory.getNullString());
        return (FLUENT) this;
    }

    @Override
    public <PARENT extends IElementFormatter> PARENT close() {
        appendClosing();
        return (PARENT) fParent;
    }

    @Override
    public int getIndentionLevel() {
        return fIndentation;
    }

    public abstract <T> ElementFormatter open(T aObject);

    protected <FLUENT extends ElementFormatter> FLUENT append(CharSequence csq) {
        try {
            fBuffer.append(csq);
            if (fBuffer instanceof Flushable) {
                ((Flushable) fBuffer).flush();
            }
        } catch (IOException aE) {
            logger.error(MarkerFactory.getMarker("EXCEPTION"), aE.getLocalizedMessage(), aE);
        }
        return (FLUENT) this;
    }

    protected <T extends ElementFormatter> T append(char c) {
        try {
            fBuffer.append(c);
            if (fBuffer instanceof Flushable) {
                ((Flushable) fBuffer).flush();
            }
        } catch (IOException aE) {
            logger.error(MarkerFactory.getMarker("EXCEPTION"), aE.getLocalizedMessage(), aE);
        }
        return (T) this;
    }

    protected <FLUENT extends ElementFormatter> FLUENT appendIndention() {
        for (int i = 0; i < fIndentation; i++) {
            append(fFactory.getIndentationString());
        }
        return (FLUENT) this;
    }

    protected abstract void appendClosing();

    protected String getClassName(Object aObject, boolean aAsSimpleName) {
        if (aAsSimpleName || fFactory.isShortClassName()) {
            return aObject.getClass().getSimpleName();
        } else {
            return aObject.getClass().getName();
        }
    }

    protected String getObjectName(Object aObject, boolean aAsSimpleName) {
        return getClassName(aObject, aAsSimpleName) + '@' + Integer.toHexString(System.identityHashCode(aObject));
    }

    protected PARENT getParent() {
        return fParent;
    }
}
