package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.AppendableFactory;
import net.workingdeveloper.java.vardump.IVarDumperFormatter;

/**
 * Created by Christoph Graupner on 2016-10-20.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class VarDumperFormatterImpl
        extends AbstractVarDumperFormatter
        implements IVarDumperFormatter {

    /**
     * @param aAppendableFactory Could be a lamba like <code>StringBuilder::new</code>.
     * @param aShortClassName    <em>true</em> if the outputted class name should be just the class itself without the package name.
     */
    public VarDumperFormatterImpl(AppendableFactory aAppendableFactory, boolean aShortClassName) {
        super(aAppendableFactory, aShortClassName);
    }
}
