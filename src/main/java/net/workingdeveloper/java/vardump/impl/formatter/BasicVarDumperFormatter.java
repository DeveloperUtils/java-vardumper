package net.workingdeveloper.java.vardump.impl.formatter;

import net.workingdeveloper.java.vardump.AppendableFactory;
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

    protected IVarDumperFormatterFactory fFactory;

    enum State {MAP, OBJECT, ARRAY, FIELD}

    protected Appendable fBuffer;
    Stack<State> fContextStack   = new Stack<>();
    boolean      fShortClassName = false;
    private AppendableFactory fBufferFactory;
    private String fRefString = "REF>>";
    private Logger logger     = LoggerFactory.getLogger(BasicVarDumperFormatter.class);

    /**
     * @param aAppendableFactory Could be a lamba like <code>StringBuilder::new</code>.
     * @param aShortClassName    <em>true</em> if the outputted class name should be just the class itself without the package name.
     * @param aFormatterFactory
     */
    protected BasicVarDumperFormatter(AppendableFactory aAppendableFactory, boolean aShortClassName, IVarDumperFormatterFactory aFormatterFactory) {
        fShortClassName = aShortClassName;
        setAppendableFactory(aAppendableFactory);
        fFactory = aFormatterFactory;
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

    public IVarDumperFormatter appendString(String aName) {
        append(aName);
        return this;
    }

    @Override
    public IVarDumperFormatter reset() {
        fBuffer = fBufferFactory.createAppendable();
        return this;
    }

    @Override
    public IVarDumperFormatter setAppendableFactory(AppendableFactory aAppendableFactory) {
        fBufferFactory = aAppendableFactory;
        fBuffer = aAppendableFactory.createAppendable();
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
