package net.workingdeveloper.java.vardump;

/**
 * Created by Christoph Graupner on 2016-10-19.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IVarDumperFormatter {

    IVarDumperFormatter appendField(String lFieldName);

    IVarDumperFormatter appendField(Object lFieldName);

    IVarDumperFormatter appendFieldValueReference(Object aObject);

    IVarDumperFormatter appendNull();

    IVarDumperFormatter appendPrimitiveFieldValue(Object aObject);

    IVarDumperFormatter appendStartDump(Object aClassOfObject);

    IVarDumperFormatter appendString(String aName);

    IVarDumperFormatter appendSubField(String aVardump);

    IVarDumperFormatter closeArray(Object aObject);

    IVarDumperFormatter closeField(String aFieldName);

    IVarDumperFormatter closeMap(Object aObject);

    IVarDumperFormatter closeObject(Object aName);

    IVarDumperFormatter openArray(Object aObject);

    IVarDumperFormatter openField(String aFieldName);

    IVarDumperFormatter openMap(Object aObject);

    IVarDumperFormatter openObject(Object aName);

    IVarDumperFormatter setStringBuffer(Appendable aBuffer);

    String toString();
}
