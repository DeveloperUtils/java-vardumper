package net.workingdeveloper.java.vardump.test.test_object.car;

/**
 * Created by Christoph Graupner on 2017-02-25.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class Tyre extends CarPart {
    float fMaxPressure;
    float fMinPressure;
    float fSize;

    public Tyre(float aSize, float aMaxPressure, float aMinPressure) {
        fMaxPressure = aMaxPressure;
        fMinPressure = aMinPressure;
        fSize = aSize;
    }
}
