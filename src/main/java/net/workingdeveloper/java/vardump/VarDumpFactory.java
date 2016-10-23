package net.workingdeveloper.java.vardump;

import net.workingdeveloper.java.vardump.impl.RecursiveVarDumperImpl;
import net.workingdeveloper.java.vardump.impl.VarDumperCyclicRegistryImpl;
import net.workingdeveloper.java.vardump.impl.VarDumperIndentFormatterImpl;

import java.lang.reflect.Field;
import java.util.function.Predicate;

/**
 * Created by Christoph Graupner on 2016-10-17.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class VarDumpFactory {

    protected static VarDumpFactory sfInstance;

    public static VarDumpFactory instance() {
        if (null == sfInstance) {
            sfInstance = new VarDumpFactory();
        }
        return sfInstance;
    }

    public Predicate<Field> createFieldAcceptPredicate(String[] aExcludeFieldNames, boolean aAppendStatics, boolean aAppendTransients) {
        return new FieldAcceptorPredicate(aExcludeFieldNames, aAppendStatics, aAppendTransients);
    }

    public IVarDumperFormatter createIndentFormatter(Appendable aAppendable, int aIndent, boolean aShortClassNames) {
        return new VarDumperIndentFormatterImpl(aAppendable, aIndent, aShortClassNames);
    }

    public IVarDumper createRecursiveDumper(IVarDumperFormatter aFormatter) {
        return new RecursiveVarDumperImpl(aFormatter, getDefaultFieldPredicate(), getDefaultCyclicRegistry());
    }

    public IVarDumper createRecursiveDumper(IVarDumperFormatter aFormatter, Predicate<Field> aFieldPredicate) {
        return new RecursiveVarDumperImpl(aFormatter, aFieldPredicate, getDefaultCyclicRegistry());
    }

    public IVarDumper createRecursiveDumper(IVarDumperFormatter aFormatter, IVarDumperCyclicRegistry aCyclicRegistry) {
        return new RecursiveVarDumperImpl(aFormatter, getDefaultFieldPredicate(), aCyclicRegistry);
    }

    public IVarDumper createRecursiveDumper() {
        return new RecursiveVarDumperImpl(
                getDefaultFormatter(), getDefaultFieldPredicate(), getDefaultCyclicRegistry());
    }

    public IVarDumperCyclicRegistry getDefaultCyclicRegistry() {
        return new VarDumperCyclicRegistryImpl();
    }

    public Predicate<Field> getDefaultFieldPredicate() {
        return aField -> true;
    }

    public IVarDumperFormatter getDefaultFormatter() {
        return new VarDumperIndentFormatterImpl(new StringBuilder(), 2, false);
    }
}
