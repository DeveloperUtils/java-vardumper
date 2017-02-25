package net.workingdeveloper.java.vardump;

import net.workingdeveloper.java.vardump.impl.RecursiveVarDumperImpl;
import net.workingdeveloper.java.vardump.impl.VarDumperCyclicRegistryImpl;
import net.workingdeveloper.java.vardump.impl.formatter.indent.FormatterFactory;

import java.lang.reflect.Field;
import java.util.function.Predicate;

/**
 * Created by Christoph Graupner on 2016-10-17.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class VarDumperFactory {

    protected static VarDumperFactory sfInstance;

    public static VarDumperFactory instance() {
        if (null == sfInstance) {
            sfInstance = new VarDumperFactory();
        }
        return sfInstance;
    }

    public Predicate<Field> createFieldAcceptPredicate(String[] aExcludeFieldNames, boolean aAppendStatics, boolean aAppendTransients) {
        return new FieldAcceptorPredicate(aExcludeFieldNames, aAppendStatics, aAppendTransients);
    }

    public IVarDumperFormatter createIndentFormatter(AppendableFactory aAppendable, int aIndent, boolean aShortClassNames) {
        final FormatterFactory lFormatterFactory = FormatterFactory.createInstance(
                aAppendable, aIndent, aShortClassNames);
        return lFormatterFactory.createVarDumperFormatter(aAppendable, aIndent, aShortClassNames);
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

    public IVarDumper createRecursiveDumper(boolean aShortNames) {
        return new RecursiveVarDumperImpl(
                createIndentFormatter(
                        getDefaultAppendableFactory(),
                        2,
                        aShortNames
                ), getDefaultFieldPredicate(), getDefaultCyclicRegistry());
    }

    public AppendableFactory getDefaultAppendableFactory() {
        return StringBuilder::new;
    }

    public IVarDumperCyclicRegistry getDefaultCyclicRegistry() {
        return new VarDumperCyclicRegistryImpl();
    }

    public Predicate<Field> getDefaultFieldPredicate() {
        return aField -> true;
    }

    public IVarDumperFormatter getDefaultFormatter() {
        return createIndentFormatter(
                getDefaultAppendableFactory(),
                2,
                false
        );
    }
}
