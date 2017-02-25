package net.workingdeveloper.java.vardump.test.test_object.car;

import java.util.List;

/**
 * Created by Christoph Graupner on 2017-02-25.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class Car extends Vehicle {
    public Car(Chassis aChassis, Engine[] aEngines, Brake[] aBrakes, Tyre[] aTyres, List<Seat> aSeats, StearingWheel aStearingWheel, int aMaxVelocity) {
        super(aChassis, aEngines, aBrakes, aTyres, aSeats, aStearingWheel, aMaxVelocity);
    }
}
