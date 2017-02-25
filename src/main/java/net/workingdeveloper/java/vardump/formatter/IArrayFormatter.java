package net.workingdeveloper.java.vardump.formatter;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IArrayFormatter extends IElementFormatter {
    IArrayEntryFormatter openEntry(Object aObject);
}
