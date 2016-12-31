package net.workingdeveloper.java.vardump.impl.formatter;

import net.workingdeveloper.java.vardump.AppendableFactory;
import net.workingdeveloper.java.vardump.IVarDumperFormatter;

/**
 * Created by Christoph Graupner on 2016-10-22.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class VarDumperIndentFormatterImpl extends AbstractVarDumperFormatter implements IVarDumperFormatter {
    private final int fIndentLevel;
    private       int fCurrentIndention;

    public VarDumperIndentFormatterImpl(AppendableFactory aBuffer, int aIndentLevel, boolean aShortClassName) {
        super(aBuffer, aShortClassName);
        fIndentLevel = aIndentLevel;
    }

    @Override
    public IVarDumperFormatter appendNull() {
        append("<null>");
        return this;
    }

    @Override
    public IVarDumperFormatter closeArray(Object aObject) {
        indentValue();
        super.closeArray(aObject);
        fCurrentIndention -= fIndentLevel;
        if (!fContextStack.empty()) {
            fContextStack.pop();
        }
        return this;
    }

    @Override
    public IVarDumperFormatter closeField(String aFieldName) {
        super.closeField(aFieldName);
        fCurrentIndention -= fIndentLevel;
        if (!fContextStack.empty()) {
            fContextStack.pop();
        }
        return append("\n");
    }

    @Override
    public IVarDumperFormatter closeMap(Object aObject) {
        indentValue();
        super.closeMap(aObject);
        fCurrentIndention -= fIndentLevel;
        if (!fContextStack.empty()) {
            fContextStack.pop();
        }
        return this;
    }

    @Override
    public IVarDumperFormatter closeObject(Object aName) {
        indentValue();
        super.closeObject(aName);
        fCurrentIndention -= fIndentLevel;
        if (!fContextStack.empty()) {
            fContextStack.pop();
        }
        return this;
    }

    @Override
    public IVarDumperFormatter openArray(Object aObject) {
        fCurrentIndention += fIndentLevel;
        fContextStack.push(State.ARRAY);
        super.openArray(aObject);
        return this;
    }

    @Override
    public IVarDumperFormatter openField(String aFieldName) {
        fCurrentIndention += fIndentLevel;
        fContextStack.push(State.FIELD);
        indentValue();
        super.openField(aFieldName);
        return this;
    }

    @Override
    public IVarDumperFormatter openMap(Object aObject) {
        fCurrentIndention += fIndentLevel;
        fContextStack.push(State.MAP);
        super.openMap(aObject);
        return append("\n");
    }

    @Override
    public IVarDumperFormatter openObject(Object aName) {
        fCurrentIndention += fIndentLevel;
        fContextStack.push(State.OBJECT);
        super.openObject(aName);
        return append("\n");
    }

    private VarDumperIndentFormatterImpl indentValue() {
        for (int i = 0; i < fCurrentIndention; i++) {
            append(' ');
        }
        return this;
    }
}
