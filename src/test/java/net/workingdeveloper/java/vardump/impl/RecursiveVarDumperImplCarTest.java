package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.VarDumperFactory;
import net.workingdeveloper.java.vardump.test.test_object.car.*;
import org.apache.commons.lang3.text.StrBuilder;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Christoph Graupner on 2017-02-25.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class RecursiveVarDumperImplCarTest {
    @Test
    public void testSimpleCar() throws Exception {
        final Brake[] lBrakes = {
                new Brake(), new Brake(), new Brake(), new Brake()
        };
        final Engine[] lEngines = {
                new Engine(90, new Gear(6), Engine.Type.ELECTRIC)
        };
        final Tyre[] lTyres = {
                new Tyre(18.0f, 3.0f, 1.4f), new Tyre(18.0f, 3.0f, 1.4f),
                new Tyre(20.0f, 3.0f, 1.4f), new Tyre(20.0f, 3.0f, 1.4f)
        };
        final ArrayList<Seat> lSeats = new ArrayList<>();
        Car lCar = new Car(
                new Chassis("Blue"), lEngines, lBrakes, lTyres, lSeats,
                new StearingWheel(19.7f), 164
        );
        System.out.printf(VarDumperFactory.instance().createRecursiveDumper(true).vardump(lCar));
    }
}
