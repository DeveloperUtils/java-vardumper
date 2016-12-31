package net.workingdeveloper.java.vardump.impl.formatter;

import net.workingdeveloper.java.vardump.AppendableFactory;
import net.workingdeveloper.java.vardump.IVarDumperFormatter;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Christoph Graupner on 2016-10-21.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
abstract public class AbstractVarDumperFormatter extends BasicVarDumperFormatter implements IVarDumperFormatter {

    /**
     * @param aAppendableFactory Could be a lamba like <code>StringBuilder::new</code>.
     * @param aShortClassName    <em>true</em> if the outputted class name should be just the class itself without the package name.
     */
    public AbstractVarDumperFormatter(AppendableFactory aAppendableFactory, boolean aShortClassName) {
        super(aAppendableFactory, aShortClassName);
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
    public IVarDumperFormatter appendField(Object lFieldName) {
        append(getObjectName(lFieldName, false));
        return this;
    }

    @Override
    public IVarDumperFormatter appendPrimitiveFieldValue(Object aObject) {
        append('(').append(aObject.getClass().getSimpleName()).append(")");
        if (aObject instanceof CharSequence) {
            append('"').append(aObject.toString()).append('"');
        } else if (aObject instanceof Character) {
            append('\'').append(aObject.toString()).append('\'');
        } else {
            append(aObject.toString());
        }
        return this;
    }

    @Override
    public IVarDumperFormatter appendStartDump(Object aObject) {
        append(getObjectName(aObject, false));
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
    public IVarDumperFormatter closeMap(Object aObject) {
        append("}");
        return this;
    }

    @Override
    public IVarDumperFormatter closeObject(Object aName) {
        append("}");
        return this;
    }

    @Override
    public IVarDumperFormatter openArray(Object aObject) {
        append(getObjectName(aObject, true));
        int lSize = -1;
        if (aObject.getClass().isArray()) {
            lSize = ((Object[]) aObject).length;
        } else if (aObject instanceof Collection) {
            lSize = ((Collection) aObject).size();
        }
        append("(").append(Integer.toString(lSize)).append(")");
        append("[");
        return this;
    }

    @Override
    public IVarDumperFormatter openField(String aFieldName) {
        append(aFieldName);
        if (!fContextStack.empty() && fContextStack.peek() == State.MAP) {
            append(": ");
        } else {
            append(" = ");
        }
        return this;
    }

    @Override
    public IVarDumperFormatter openMap(Object aObject) {
        append(getObjectName(aObject, true));
        append(" {");
        return this;
    }

    @Override
    public IVarDumperFormatter openObject(Object aName) {
        append(getObjectName(aName, false));
        append(" {");
        return this;
    }
}
