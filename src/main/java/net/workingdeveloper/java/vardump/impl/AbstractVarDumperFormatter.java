package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.IVarDumperFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.io.IOException;

/**
 * Created by Christoph Graupner on 2016-10-21.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
abstract public class AbstractVarDumperFormatter implements IVarDumperFormatter {

    protected Appendable fBuffer;
    private Logger logger = LoggerFactory.getLogger(VarDumperFormatterImpl.class);

    public AbstractVarDumperFormatter(Appendable aBuffer) {
        fBuffer = aBuffer;
    }

    @Override
    public IVarDumperFormatter appendField(String lFieldName) {
        try {
            fBuffer.append(lFieldName);
        } catch (IOException aE) {
        }
        return this;
    }

    @Override
    public IVarDumperFormatter appendFieldValue(Object aObject) {
        append(aObject.toString());
        return this;
    }

    @Override
    public IVarDumperFormatter appendFieldValueReference(Object aObject) {
        append("ref:").append(aObject.getClass().getName())
                      .append('@')
                      .append(Integer.toHexString(System.identityHashCode(aObject)));
        return this;
    }

    @Override
    public IVarDumperFormatter appendNull() {
        append("<null>");
        return this;
    }

    @Override
    public IVarDumperFormatter appendStartDump(String fName, Object aClassOfObject) {
        append(fName)
                .append('@')
                .append(Integer.toHexString(System.identityHashCode(aClassOfObject)));
        return this;
    }

    @Override
    public IVarDumperFormatter appendSubField(String aVardump) {
        return null;
    }

    @Override
    public IVarDumperFormatter closeArray(Object aObject) {
        append("]");
        return this;
    }

    @Override
    public IVarDumperFormatter closeField(String aFieldName) {
        append(";");
        return this;
    }

    @Override
    public IVarDumperFormatter closeObject(String aName) {
        append("}");
        return this;
    }

    @Override
    public IVarDumperFormatter openArray(Object aObject) {
        append("[");
        return this;
    }

    @Override
    public IVarDumperFormatter openField(String aFieldName) {
        append(aFieldName).append(" = ");
        return this;
    }

    @Override
    public IVarDumperFormatter openObject(String aName) {
        append("{");
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

    protected VarDumperFormatterImpl append(CharSequence csq) {
        try {
            fBuffer.append(csq);
        } catch (IOException aE) {
            logger.error(MarkerFactory.getMarker("EXCEPTION"), aE.getLocalizedMessage(), aE);
        }
        return this;
    }

    protected VarDumperFormatterImpl append(char c) {
        try {
            fBuffer.append(c);
        } catch (IOException aE) {
            logger.error(MarkerFactory.getMarker("EXCEPTION"), aE.getLocalizedMessage(), aE);
        }
        return this;
    }
}
