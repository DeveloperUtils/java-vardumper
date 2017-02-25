package net.workingdeveloper.java.vardump.test.test_object.car;

/**
 * Created by Christoph Graupner on 2017-02-25.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class Engine extends CarPart {
    public enum Type {
        GASOLINE, ELECTRIC
    }
    final Type fType;
    Gear fGear;
    int  fPower;

    public Engine(int aPower, Gear aGear, Type aType) {
        fPower = aPower;
        fGear = aGear;
        fType = aType;
    }
}

