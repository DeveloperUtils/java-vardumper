package net.workingdeveloper.java.vardump.impl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import net.workingdeveloper.java.vardump.test.test_object.TestPrimitives;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static net.workingdeveloper.java.vardump.test.VarDumpMatcher.matchesVarDump;
import static org.junit.Assert.assertThat;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
@RunWith(DataProviderRunner.class)
public class RecursiveVarDumperImplListTest extends BaseTest {
    @DataProvider
    public static Object[][] dataProviderVardumpArray() {
        return new Object[][]{
                {
                        new String[]{"h1", "h2"},
                        "String[]@00000000(2)[(String)\"h1\",(String)\"h2\"]"
                }, {
                        new Object[]{1, 0.1f, 0.3d, true, "hallo", 'C'},
                        "Object[]@00000000(6)[(Integer)1,(Float)0.1,(Double)0.3,(Boolean)true,(String)\"hallo\",(Character)'C']"
                }
        };
    }

    @DataProvider
    public static Object[][] dataProviderVardumpArrayNull() {
        return new Object[][]{
                {
                        new Object[]{},
                        "Object[]@00000000(0)[]"
                }, {
                        new Boolean[]{},
                        "Boolean[]@00000000(0)[]"
                }, {
                        new String[]{},
                        "String[]@00000000(0)[]"
                }, {
                        new Object[0],
                        "Object[]@00000000(0)[]"
                }
        };
    }

    @Test
    @UseDataProvider("dataProviderVardumpArray")
    public void vardumpArray(Object[] aInput, String aExpected) throws Exception {
        RecursiveVarDumperImpl lSut = getRecursiveVarDumperSut();
        assertThat(lSut.vardump(aInput), matchesVarDump(aExpected));
    }

    @Test
    @UseDataProvider("dataProviderVardumpArrayNull")
    public void vardumpArrayNull(Object[] aInput, String aExpected) throws Exception {
        RecursiveVarDumperImpl lSut = getRecursiveVarDumperSut();

        assertThat(lSut.vardump(aInput), matchesVarDump(aExpected));
    }

    public void vardumpArrayObjects() throws Exception {
//        TestPrimitives
    }

    @Test
    public void vardumpListNull() throws Exception {
        RecursiveVarDumperImpl lSut = getRecursiveVarDumperSut();
        List<TestPrimitives>   d2   = new ArrayList<>();
        System.out.println(lSut.vardump(d2));
        assertThat(lSut.vardump(d2), matchesVarDump("ArrayList@6a38e57f(0)[]"));
    }

    @Test
    public void vardumpListPrimitiveObjects() throws Exception {
        RecursiveVarDumperImpl lSut = getRecursiveVarDumperSut();
        List<TestPrimitives>   d2   = new ArrayList<>();
        System.out.println(lSut.vardump(d2));
        assertThat(lSut.vardump(d2), matchesVarDump("ArrayList@6a38e57f(0)[]"));

        d2 = new ArrayList<>();
        d2.add(new TestPrimitives(true, Byte.MAX_VALUE, 'g', 0.3d, 0.4f, Integer.MAX_VALUE, Short.MAX_VALUE, "hallo"));
        d2.add(new TestPrimitives(
                false, Byte.MIN_VALUE, 'l', -0.3d, -0.4f, Integer.MIN_VALUE, Short.MIN_VALUE, "tschüß"));
        System.out.println(lSut.vardump(d2));
        assertThat(lSut.vardump(d2), matchesVarDump(
                "ArrayList@6a38e57f(2)[" +
                        "TestPrimitives@0000{fBool = (boolean)true; fByte = (byte)127; fChar = (char)'g'; fDouble = (double)0.3;" +
                        " fFloat = (float)0.4; fInt = (int)2147483647; fShort = (short)32767; fString = (String)\"hallo\"; }," +
                        "TestPrimitives@0000{fBool = (boolean)false; fByte = (byte)-128; fChar = (char)'l'; fDouble = (double)-0.3;" +
                        " fFloat = (float)-0.4; fInt = (int)-2147483648; fShort = (short)-32768; fString = (String)\"tschüß\"; }" +
                        "]")
        );
    }
}
