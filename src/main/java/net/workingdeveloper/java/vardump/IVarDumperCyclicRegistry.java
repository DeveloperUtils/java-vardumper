package net.workingdeveloper.java.vardump;

/**
 * Created by Christoph Graupner on 2016-10-19.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IVarDumperCyclicRegistry {
    IVarDumperCyclicRegistry clear();

    boolean isRegistered(Object aObject);

    IVarDumperCyclicRegistry register(Object aObject);
}
