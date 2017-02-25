package net.workingdeveloper.java.vardump.test.test_object.car;

/**
 * Created by Christoph Graupner on 2017-02-25.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class Chassis extends CarPart {
    String fColor;

    public Chassis(String aColor) {
        fColor = aColor;
    }

    public String getColor() {
        return fColor;
    }

    public void setColor(String aColor) {
        fColor = aColor;
    }
}
