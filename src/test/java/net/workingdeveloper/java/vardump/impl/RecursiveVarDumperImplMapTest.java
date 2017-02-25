package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.test.test_object.TestPrimitives;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static net.workingdeveloper.java.vardump.test.VarDumpMatcher.matchesVarDump;
import static org.junit.Assert.assertThat;

/**
 * Created by Christoph Graupner on 2017-02-25.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class RecursiveVarDumperImplMapTest extends BaseTest {
    @Test
    public void vardumpMapNull() throws Exception {
        RecursiveVarDumperImpl      lSut = getRecursiveVarDumperSut();
        Map<String, TestPrimitives> d2   = new HashMap<>();
        System.out.println(lSut.vardump(d2));
        assertThat(lSut.vardump(d2), matchesVarDump("HashMap@0000{\n}"));
    }

    @Test
    public void vardumpMapPrimitives() throws Exception {
        RecursiveVarDumperImpl lSut    = getRecursiveVarDumperSut();
        Map<String, Object>    lMap    = new HashMap<>();
        boolean                fBool   = true;
        byte                   fByte   = 127;
        double                 fDouble = 0x1.fffffffffffffP+1023;
        float                  fFloat  = 0x1.fffffeP+127f;
        int                    fInt    = 2147483647;
        short                  fShort  = 32767;
        long                   fLong   = 0x7fffffffffffffffL;
        char                   lChar   = 'f';
        String                 fString = "local";

        lMap.put("fBool", fBool);
        lMap.put("fByte", fByte);
        lMap.put("fDouble", fDouble);
        lMap.put("fFloat", fFloat);
        lMap.put("fInt", fInt);
        lMap.put("fShort", fShort);
        lMap.put("fLong", fLong);
        lMap.put("lChar", lChar);
        lMap.put("fString", fString);

        System.out.println(lSut.vardump(lMap));
        assertThat(lSut.vardump(lMap), matchesVarDump(
                "HashMap@0000{" +
                        "\n  fString: (String)\"local\"," +
                        "\n  fByte: (Byte)127," +
                        "\n  fLong: (Long)9223372036854775807," +
                        "\n  lChar: (Character)'f'," +
                        "\n  fInt: (Integer)2147483647," +
                        "\n  fShort: (Short)32767," +
                        "\n  fBool: (Boolean)true," +
                        "\n  fFloat: (Float)3.4028235E38," +
                        "\n  fDouble: (Double)1.7976931348623157E308" +
                        "\n}"));
    }

    @Test
    public void vardumpMapSimpleObjects() throws Exception {
        RecursiveVarDumperImpl      lSut = getRecursiveVarDumperSut();
        Map<String, TestPrimitives> d2   = new HashMap<>();
        d2.put(
                "t1",
                new TestPrimitives(true, Byte.MAX_VALUE, 'g', 0.3d, 0.4f, Integer.MAX_VALUE, Short.MAX_VALUE, "hallo")
        );
        System.out.println(lSut.vardump(d2));
        assertThat(lSut.vardump(d2), matchesVarDump(
                "HashMap@0000{" +
                        "\n  t1: TestPrimitives@00000000{" +
                        "\n    fBool = (boolean)true;" +
                        "\n    fByte = (byte)127;" +
                        "\n    fChar = (char)'g';" +
                        "\n    fDouble = (double)0.3;" +
                        "\n    fFloat = (float)0.4;" +
                        "\n    fInt = (int)2147483647;" +
                        "\n    fShort = (short)32767;" +
                        "\n    fString = (String)\"hallo\";" +
                        "\n  }" +
                        "\n}")
        );
    }
}
