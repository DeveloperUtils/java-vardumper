package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.impl.test_object.*;
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
        assertThat(lSut.vardump(0.1f), equalTo("(Float)0.1"));
        assertThat(lSut.vardump(0.1d), equalTo("(Double)0.1"));
        assertThat(lSut.vardump(true), equalTo("(Boolean)true"));
        assertThat(lSut.vardump(false), equalTo("(Boolean)false"));
    }

    @Test
    public void vardumpSimpleClasses() throws Exception {
        RecursiveVarDumperImpl lSut = getRecursiveVarDumperSut();
        Object                 d1   = new TestEmpty();
        assertThat(
                lSut.vardump(d1),
                matchesVarDump("TestEmpty@00000000 {}")
        );
        d1 = new TestPrimitives();
        assertThat(
                lSut.vardump(d1),
                matchesVarDump("TestPrimitives@00000000 {}")
        );
        System.out.println(lSut.vardump(d1));
    }
}
