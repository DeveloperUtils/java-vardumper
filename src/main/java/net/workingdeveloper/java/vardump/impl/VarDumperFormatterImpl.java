package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.IVarDumperFormatter;

/**
 * Created by Christoph Graupner on 2016-10-20.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class VarDumperFormatterImpl
        extends AbstractVarDumperFormatter
        implements IVarDumperFormatter {

    public VarDumperFormatterImpl(Appendable aBuffer) {
        super(aBuffer);
    }
}
