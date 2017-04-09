package net.workingdeveloper.java.vardump.formatter;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IElementFormatter {

    enum Literals {
        NEW_LINE, CLOSE_OBJECT, OPEN_OBJECT, OPEN_MAP, CLOSE_MAP, CLOSE_ARRAY, OPEN_ARRAY, LINE_COMMENT
    }

    <FLUENT extends IElementFormatter> FLUENT appendComment(String aComment);

    <FLUENT extends IElementFormatter> FLUENT appendFieldValueReference(Object aObject);

    <FLUENT extends IElementFormatter> FLUENT appendNull();

    <PARENT extends IElementFormatter> PARENT close();

    int getIndentionLevel();

    String getLiteral(final Literals aLiteralId);

    String toString();
}
