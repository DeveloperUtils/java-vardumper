package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.AppendableFactory;
import net.workingdeveloper.java.vardump.IVarDumperFormatter;
import net.workingdeveloper.java.vardump.formatter.IElementFormatter;

/**
 * Created by Christoph Graupner on 2016-10-22.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class VarDumperIndentFormatterImpl extends ContainerElementFormatter<IElementFormatter> implements IVarDumperFormatter {
    private final AppendableFactory fAppendableFactory;

    /**
     * @param aAppendableFactory Could be a lamba like <code>StringBuilder::new</code>.
     * @param aIndentString
     * @param aShortClassName    <em>true</em> if the outputted class name should be just the class itself without the package name.
     */
    VarDumperIndentFormatterImpl(
            AppendableFactory aAppendableFactory,
            String aIndentString,
            boolean aShortClassName
    ) {
        this(
                aAppendableFactory,
                FormatterFactory.createInstance(aAppendableFactory, aIndentString, aShortClassName)
        );
    }

    VarDumperIndentFormatterImpl(
            AppendableFactory aAppendableFactory,
            FormatterFactory aFormatterFactory
    ) {
        super(0, null, null,
              aFormatterFactory
        );
        fAppendableFactory = aAppendableFactory;
        fBuffer = aAppendableFactory.createAppendable();
    }

    @Override
    public IVarDumperFormatter endDump() {
        return this;
    }

    @Override
    public <T> ElementFormatter open(T aObject) {
        return null;
    }

    @Override
    public IVarDumperFormatter reset() {
        fBuffer = fAppendableFactory.createAppendable();
        return this;
    }

    @Override
    public IVarDumperFormatter setAppendableFactory(AppendableFactory aAppendableFactory) {
        return this;
    }

    @Override
    public IVarDumperFormatter startDump() {
        return this;
    }

    @Override
    public String toString() {
        return fBuffer.toString();
    }

    @Override
    protected void appendClosing() {

    }
}
