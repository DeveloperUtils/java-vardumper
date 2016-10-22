package net.workingdeveloper.java.vardump.impl;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christoph Graupner on 2016-10-17.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class RecursiveVarDumperImplTest {

    class TestCyclic {
        TestCyclic fChild;
        TestCyclic fParent;

        public TestCyclic(TestCyclic aParent) {
            fParent = aParent;
            if (fParent != null) { fParent.fChild = this; }
        }
    }

    class TestEmpty {

    }

    class TestHierarchyRoot {

    }

    class TestHierarchySub extends TestHierarchyRoot {

    }

    class TestPrimitives {

    }

    @Test
    public void testCyclic() throws Exception {
        TestCyclic lParent0 = new TestCyclic(null);
        TestCyclic lParent1 = new TestCyclic(lParent0);
        TestCyclic lChild   = new TestCyclic(lParent1);
        RecursiveVarDumperImpl lSut = new RecursiveVarDumperImpl(
                new VarDumperFormatterImpl(new StringBuilder()),
                new VarDumperCyclicRegistryImpl()
        );
        System.out.println(lSut.vardump(lParent0));
        System.out.println(lSut.vardump(lParent1));
        System.out.println(lSut.vardump(lChild));
    }

    @Test
    public void vardumpInheritedClasses() throws Exception {
        RecursiveVarDumperImpl lSut = new RecursiveVarDumperImpl(
                new VarDumperFormatterImpl(new StringBuilder()),
                new VarDumperCyclicRegistryImpl()
        );
        Object d1 = new TestHierarchyRoot();
        System.out.println(lSut.vardump(d1));
        d1 = new TestHierarchySub();
        System.out.println(lSut.vardump(d1));
    }

    @Test
    public void vardumpLists() throws Exception {
        RecursiveVarDumperImpl lSut = new RecursiveVarDumperImpl(
                new VarDumperFormatterImpl(new StringBuilder()),
                new VarDumperCyclicRegistryImpl()
        );
        Object[] d1 = new TestPrimitives[5];
        System.out.println(lSut.vardump(d1));
        List<TestPrimitives> d2 = new ArrayList<>();
        System.out.println(lSut.vardump(d1));
    }

    @Test
    public void vardumpPrimitives() throws Exception {
        RecursiveVarDumperImpl lSut = new RecursiveVarDumperImpl(
                new VarDumperFormatterImpl(new StringBuilder()),
                new VarDumperCyclicRegistryImpl()
        );
        System.out.println(lSut.vardump("hallo"));
        System.out.println(lSut.vardump(1));
    }

    @Test
    public void vardumpSimpleClasses() throws Exception {
        RecursiveVarDumperImpl lSut = new RecursiveVarDumperImpl(
                new VarDumperFormatterImpl(new StringBuilder()),
                new VarDumperCyclicRegistryImpl()
        );
        Object d1 = new TestEmpty();
        System.out.println(lSut.vardump(d1));
        d1 = new TestPrimitives();
        System.out.println(lSut.vardump(d1));
    }
}
