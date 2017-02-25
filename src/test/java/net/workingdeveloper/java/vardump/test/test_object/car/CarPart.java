package net.workingdeveloper.java.vardump.test.test_object.car;

/**
 * Created by Christoph Graupner on 2017-02-25.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
abstract public class CarPart {
    Vehicle fOwningVehicle;

    public Vehicle getOwningVehicle() {
        return fOwningVehicle;
    }

    public void setOwningVehicle(Vehicle aOwningVehicle) {
        fOwningVehicle = aOwningVehicle;
    }
}
