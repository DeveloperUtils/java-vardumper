package net.workingdeveloper.java.vardump;

import net.workingdeveloper.java.vardump.formatter.IContainerElementFormatter;

/**
 * Created by Christoph Graupner on 2016-10-19.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IVarDumperFormatter extends IContainerElementFormatter {

    IVarDumperFormatter endDump();

    IVarDumperFormatter reset();

    IVarDumperFormatter setAppendableFactory(AppendableFactory aAppendableFactory);

    IVarDumperFormatter startDump();
}
