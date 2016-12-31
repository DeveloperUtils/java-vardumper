package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.impl.formatter.VarDumperFormatterImpl;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
abstract class BaseTest {
    protected RecursiveVarDumperImpl getRecursiveVarDumperSut() {
        return new RecursiveVarDumperImpl(
                new VarDumperFormatterImpl(StringBuilder::new, true),
                aField -> true,
                new VarDumperCyclicRegistryImpl()
        );
    }
}
