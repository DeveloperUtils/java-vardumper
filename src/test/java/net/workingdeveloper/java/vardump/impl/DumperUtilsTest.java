package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.test.test_object.TestEmpty;
import net.workingdeveloper.java.vardump.test.test_object.TestPrimitives;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Christoph Graupner on 2017-01-15.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class DumperUtilsTest {
    @Test
    public void isPrimitiveTypeClass() throws Exception {
        TestPrimitives lObj = new TestPrimitives(
                true, Byte.MAX_VALUE, 'g', 0.3d, 0.4f, Integer.MAX_VALUE, Short.MAX_VALUE, "hallo");
        final Field[] fields = lObj.getClass().getDeclaredFields();
        for (Field lField : fields) {
            if (lField.getName().equals("fString")) {
                assertThat(lField.getName(), DumperUtils.isPrimitiveType(lField.getType()), is(true));
            }
        }

        Class[] lPrimitiveClasses = new Class[]{String.class, Boolean.class, Integer.class, Character.class,
                                                Float.class, Number.class};
        for (Class lPrimitiveClass : lPrimitiveClasses) {
            assertThat(lPrimitiveClass.getName(), DumperUtils.isPrimitiveType(lPrimitiveClass), is(true));
        }
        Class[] lNonPrimitiveClasses = new Class[]{Math.class, TestPrimitives.class, TestEmpty.class, Object.class, Class.class};
        for (Class lNonPrimitiveClass : lNonPrimitiveClasses) {
            assertThat(lNonPrimitiveClass.getName(), DumperUtils.isPrimitiveType(lNonPrimitiveClass), is(false));
        }
    }

    @Test
    public void isPrimitiveTypeField() throws Exception {
        TestPrimitives lObj = new TestPrimitives(
                true, Byte.MAX_VALUE, 'g', 0.3d, 0.4f, Integer.MAX_VALUE, Short.MAX_VALUE, "hallo");
        final Field[] fields = lObj.getClass().getDeclaredFields();
        for (Field lField : fields) {
            if (lField.getName().equals("fString")) {
                assertThat(lField.getName(), DumperUtils.isPrimitiveType(lField), is(true));
            }
        }
    }

    @Test
    public void isPrimitiveTypeObject() throws Exception {
        assertThat(DumperUtils.isPrimitiveType(1), is(true));
        assertThat(DumperUtils.isPrimitiveType(0.1d), is(true));
        assertThat(DumperUtils.isPrimitiveType("Fll"), is(true));
    }
}
