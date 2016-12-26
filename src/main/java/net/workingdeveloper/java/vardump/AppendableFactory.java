package net.workingdeveloper.java.vardump;

/**
 * Created by Christoph Graupner on 2016-12-04.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public interface AppendableFactory {
    /**
     * Every time it is called it gives a <b>new</b> instance of an Appendable implementation
     *
     * @return a new instance of an implementation of Appendable
     */
    Appendable createAppendable();
}
