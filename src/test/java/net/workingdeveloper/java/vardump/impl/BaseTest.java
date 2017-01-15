package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.impl.formatter.indent.FormatterFactory;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
abstract class BaseTest {
    protected RecursiveVarDumperImpl getRecursiveVarDumperSut() {
        return new RecursiveVarDumperImpl(
                FormatterFactory.createInstance(StringBuilder::new, 2, true)
                                .createVarDumperFormatter(),
                aField -> true,
                new VarDumperCyclicRegistryImpl()
        );
    }
}
