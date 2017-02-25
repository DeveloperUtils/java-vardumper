package net.workingdeveloper.java.vardump.test.test_object.car;

/**
 * Created by Christoph Graupner on 2017-02-25.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class Gear extends CarPart {
    int fLevels;
    Engine fEngine;

    public Gear(int aLevels) {
        fLevels = aLevels;
    }

    public Engine getEngine() {
        return fEngine;
    }

    public void setEngine(Engine aEngine) {
        fEngine = aEngine;
    }
}
