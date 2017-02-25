package net.workingdeveloper.java.vardump.test.test_object.car;

/**
 * Created by Christoph Graupner on 2017-02-25.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class Seat extends CarPart {
    enum Type {
        SPORT, NORMAL
    }

    double fMaxWeight;
    float  fSize;
    Type   fType;

    public Seat(double aMaxWeight, float aSize, Type aType) {
        fMaxWeight = aMaxWeight;
        fSize = aSize;
        fType = aType;
    }
}
