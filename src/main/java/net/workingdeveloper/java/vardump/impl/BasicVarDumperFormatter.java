package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.IVarDumperFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.io.Flushable;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by Christoph Graupner on 2016-10-22.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
abstract public class BasicVarDumperFormatter implements IVarDumperFormatter {

    enum State {MAP, OBJECT, ARRAY, FIELD}

    protected Appendable fBuffer;
    Stack<State> fContextStack   = new Stack<>();
    boolean      fShortClassName = false;
    private String fRefString = "REF>>";
    private Logger logger     = LoggerFactory.getLogger(VarDumperFormatterImpl.class);

    public BasicVarDumperFormatter(Appendable aBuffer, boolean aShortClassName) {
        fBuffer = aBuffer;
        fShortClassName = aShortClassName;
    }

    @Override
    public IVarDumperFormatter appendFieldValueReference(Object aObject) {
        append(fRefString).append(getObjectName(aObject, false));
        return this;
    }

    @Override
    public IVarDumperFormatter appendNull() {
        append("<null>");
        return this;
    }

    @Override
    public IVarDumperFormatter appendString(String aName) {
        append(aName);
        return this;
    }

    @Override
    public IVarDumperFormatter setStringBuffer(Appendable aBuffer) {
        fBuffer = aBuffer;
        return this;
    }

    @Override
    public String toString() {
        return fBuffer.toString();
    }

    protected <T extends BasicVarDumperFormatter> T append(CharSequence csq) {
        try {
            fBuffer.append(csq);
            if (fBuffer instanceof Flushable) {
                ((Flushable) fBuffer).flush();
            }
        } catch (IOException aE) {
            logger.error(MarkerFactory.getMarker("EXCEPTION"), aE.getLocalizedMessage(), aE);
        }
        return (T) this;
    }

    protected <T extends BasicVarDumperFormatter> T append(char c) {
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

    protected String getClassName(Object aObject, boolean aAsSimpleName) {
        if (aAsSimpleName || fShortClassName) {
            return aObject.getClass().getSimpleName();
        } else {
            return aObject.getClass().getName();
        }
    }

    protected String getObjectName(Object aObject, boolean aAsSimpleName) {
        return getClassName(aObject, aAsSimpleName) + '@' + Integer.toHexString(System.identityHashCode(aObject));
    }
}
