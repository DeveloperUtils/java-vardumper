package net.workingdeveloper.java.vardump;

import net.workingdeveloper.java.vardump.impl.RecursiveVarDumperImpl;
import net.workingdeveloper.java.vardump.impl.VarDumperFormatterImpl;

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

    public IVarDumperFormatter getDefaultFormatter() {
        return new VarDumperFormatterImpl(new StringBuilder());
    }

    public IVarDumper createRecursiveDumper() {
        return new RecursiveVarDumperImpl(getDefaultFormatter(), getDefaultCyclicRegistry());
    }

    public IVarDumperCyclicRegistry getDefaultCyclicRegistry() {
        return null;
    }
}
