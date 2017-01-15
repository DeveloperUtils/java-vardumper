package net.workingdeveloper.java.vardump.formatter;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IElementFormatter {

    <FLUENT extends IElementFormatter> FLUENT appendComment(String aComment);

    <FLUENT extends IElementFormatter> FLUENT appendFieldValueReference(Object aObject);

    <FLUENT extends IElementFormatter> FLUENT appendNull();

    <PARENT extends IElementFormatter> PARENT close();


    String toString();
}
