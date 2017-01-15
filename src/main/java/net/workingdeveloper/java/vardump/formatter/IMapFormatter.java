package net.workingdeveloper.java.vardump.formatter;

import java.util.Map;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface IMapFormatter extends IElementFormatter {
    IMapEntryFormatter openEntry(Map.Entry<?,?> aObject);
}
