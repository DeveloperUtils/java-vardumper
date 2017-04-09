package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.test.test_object.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static net.workingdeveloper.java.vardump.test.VarDumpMatcher.matchesVarDump;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Christoph Graupner on 2016-10-17.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class RecursiveVarDumperImplTest extends BaseTest {

    @Test
    public void testCyclic() throws Exception {
        TestCyclic             lParent0 = new TestCyclic(null);
        TestCyclic             lParent1 = new TestCyclic(lParent0);
        TestCyclic             lChild   = new TestCyclic(lParent1);
        RecursiveVarDumperImpl lSut     = getRecursiveVarDumperSut();
        //@fixme better matchesVarDump '"TestCyclic@0000{fChild = TestCyclic@0001{fChild = <null>; fParent = REF>>TestCyclic@0000; };, fParent = null}"'
        assertThat(
                lSut.vardump(lParent0),
                matchesVarDump(
                        "TestCyclic@00000000{"
                                + "\n  fChild = TestCyclic@00000000{"
                                + "\n    fChild = TestCyclic@00000000{"
                                + "\n      fChild = <null>;"
                                + "\n      fParent = REF>>TestCyclic@00000000;"
                                + "\n    } //TestCyclic@00000000;"
                                + "\n    fParent = REF>>TestCyclic@00000000;"
                                + "\n  } //TestCyclic@00000000;"
                                + "\n  fParent = <null>;"
                                + "\n} //TestCyclic@00000000"
                )
        );
        System.out.println(lSut.vardump(lParent0));
        System.out.println(lSut.vardump(lParent1));
        System.out.println(lSut.vardump(lChild));
    }

    @Test
    public void vardumpInheritedClasses() throws Exception {
        RecursiveVarDumperImpl lSut = getRecursiveVarDumperSut();
        Object                 d1   = new TestHierarchyRoot();
        System.out.println(lSut.vardump(d1));
        d1 = new TestHierarchySub();
        System.out.println(lSut.vardump(d1));
    }

    @Test
    public void vardumpMaps() throws Exception {
        RecursiveVarDumperImpl lSut = getRecursiveVarDumperSut();
        Object[]               d1   = new TestPrimitives[5];
        System.out.println(lSut.vardump(d1));
        List<TestPrimitives> d2 = new ArrayList<>();
        System.out.println(lSut.vardump(d1));
    }

    @Test
    public void vardumpPrimitives() throws Exception {
        RecursiveVarDumperImpl lSut = getRecursiveVarDumperSut();
        assertThat(lSut.vardump("hallo"), equalTo("(String)\"hallo\""));
        assertThat(lSut.vardump('D'), equalTo("(Character)'D'"));
        assertThat(lSut.vardump(1), equalTo("(Integer)1"));
        assertThat(lSut.vardump(1L), equalTo("(Long)1"));
        assertThat(lSut.vardump(0.1f), equalTo("(Float)0.1"));
        assertThat(lSut.vardump(0.1d), equalTo("(Double)0.1"));
        assertThat(lSut.vardump(true), equalTo("(Boolean)true"));
        assertThat(lSut.vardump(false), equalTo("(Boolean)false"));
        boolean fBool   = true;
        byte    fByte   = 127;
        double  fDouble = 0x1.fffffffffffffP+1023;
        float   fFloat  = 0x1.fffffeP+127f;
        int     fInt    = 2147483647;
        short   fShort  = 32767;
        long    fLong   = 0x7fffffffffffffffL;
        char    lChar   = 'f';
        String  fString = "local";
        assertThat(lSut.vardump(fString), equalTo("(String)\"local\""));
        assertThat(lSut.vardump(lChar), equalTo("(Character)'f'"));
        assertThat(lSut.vardump(fInt), equalTo("(Integer)2147483647"));
//        assertThat(lSut.vardump(fLong), equalTo("(Long)0x7fffffffffffffffL"));
        assertThat(lSut.vardump(fLong), equalTo("(Long)9223372036854775807"));
        assertThat(lSut.vardump(fBool), equalTo("(Boolean)true"));
        assertThat(lSut.vardump(fByte), equalTo("(Byte)127"));
        assertThat(lSut.vardump(fShort), equalTo("(Short)32767"));
        assertThat(lSut.vardump(fFloat), equalTo("(Float)3.4028235E38"));
        assertThat(lSut.vardump(fDouble), equalTo("(Double)1.7976931348623157E308"));
    }

    @Test
    public void vardumpSimpleClasses() throws Exception {
        RecursiveVarDumperImpl lSut = getRecursiveVarDumperSut();
        Object                 d1   = new TestEmpty();
        assertThat(
                lSut.vardump(d1),
                matchesVarDump("TestEmpty@0000{} //TestEmpty@00000000")
        );
        d1 = new TestPrimitives();

        assertThat(
                lSut.vardump(d1),
                matchesVarDump(
                        "TestPrimitives@0000{fBool = (boolean)false; fByte = (byte)0; fChar = (char)'" + String.valueOf(
                                (char) 0) + "'; fDouble = (double)0.0;" +
                                " fFloat = (float)0.0; fInt = (int)0; fShort = (short)0; fString = (String)<null>; } //TestPrimitives@00000000")
        );
        d1 = new TestPrimitives(true, Byte.MAX_VALUE, 'g', 0.3d, 0.4f, Integer.MAX_VALUE, Short.MAX_VALUE, "hallo");
        assertThat(

                lSut.vardump(d1),
                matchesVarDump(
                        "TestPrimitives@0000{fBool = (boolean)true; fByte = (byte)127; fChar = (char)'g'; fDouble = (double)0.3;" +
                                " fFloat = (float)0.4; fInt = (int)2147483647; fShort = (short)32767; fString = (String)\"hallo\"; }")
        );
    }
}
