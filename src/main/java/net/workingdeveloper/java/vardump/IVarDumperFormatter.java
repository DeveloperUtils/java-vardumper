package net.workingdeveloper.java.vardump;

/**
 * Created by Christoph Graupner on 2016-10-19.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IVarDumperFormatter {

    IVarDumperFormatter appendFieldValueReference(Object aObject);

    IVarDumperFormatter appendFieldValue(Object aObject);

    IVarDumperFormatter appendNull();

    IVarDumperFormatter appendSubField(String aVardump);

    IVarDumperFormatter closeArray(Object aObject);

    IVarDumperFormatter closeField(String aFieldName);

    IVarDumperFormatter closeObject(String aName);

    IVarDumperFormatter openArray(Object aObject);

    IVarDumperFormatter openField(String aFieldName);

    IVarDumperFormatter openObject(String aName);

    IVarDumperFormatter setStringBuffer(Appendable aBuffer);

    IVarDumperFormatter appendField(String lFieldName);

    IVarDumperFormatter appendStartDump(String fName, Object aClassOfObject);

    String toString();
}
