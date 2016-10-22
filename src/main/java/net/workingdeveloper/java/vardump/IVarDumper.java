package net.workingdeveloper.java.vardump;

/**
 * Created by Christoph Graupner on 2016-10-17.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IVarDumper {
    String vardump(Object aObject);

    String vardump(Object aObject, IVarDumperFormatter aStringBuilder);
}
