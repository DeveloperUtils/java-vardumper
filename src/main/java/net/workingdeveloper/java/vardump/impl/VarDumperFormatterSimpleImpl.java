package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.AppendableFactory;
import net.workingdeveloper.java.vardump.IVarDumperFormatter;

/**
 * Created by Christoph Graupner on 2016-10-21.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class VarDumperFormatterSimpleImpl
        extends AbstractVarDumperFormatter
        implements IVarDumperFormatter {
    public VarDumperFormatterSimpleImpl(AppendableFactory aAppendableFactory, boolean aShortClassName) {
        super(aAppendableFactory, aShortClassName);
    }
}
