package net.workingdeveloper.java.vardump.impl;

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
public class RecursiveVarDumperImplTest {

    class TestCyclic {
        TestCyclic fChild;
        TestCyclic fParent;

        public TestCyclic(TestCyclic aParent) {
            fParent = aParent;
            if (fParent != null) {
                fParent.fChild = this;
            }
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
                new VarDumperFormatterImpl(StringBuilder::new, true),
                field -> true,
                new VarDumperCyclicRegistryImpl()
        );
        System.out.println(lSut.vardump(lParent0));
        System.out.println(lSut.vardump(lParent1));
        System.out.println(lSut.vardump(lChild));
    }

    @Test
    public void vardumpInheritedClasses() throws Exception {
        RecursiveVarDumperImpl lSut = new RecursiveVarDumperImpl(
                new VarDumperFormatterImpl(StringBuilder::new, true),
                field -> true,
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
                new VarDumperFormatterImpl(StringBuilder::new, true),
                aField -> true,
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
                new VarDumperFormatterImpl(StringBuilder::new, true),
                aField -> true,
                new VarDumperCyclicRegistryImpl()
        );
        assertThat(lSut.vardump("hallo"), equalTo("(String)\"hallo\""));
        assertThat(lSut.vardump(1), equalTo("(Integer)1"));
    }

    @Test
    public void vardumpSimpleClasses() throws Exception {
        RecursiveVarDumperImpl lSut = new RecursiveVarDumperImpl(
                new VarDumperFormatterImpl(StringBuilder::new, true),
                aField -> true,
                new VarDumperCyclicRegistryImpl()
        );
        Object d1 = new TestEmpty();
        assertThat(
                lSut.vardump(d1),
                matchesVarDump("TestEmpty@00000000 {this$0 = RecursiveVarDumperImplTest@00000000 {};}")
        );
        System.out.println(lSut.vardump(d1));
        d1 = new TestPrimitives();
        System.out.println(lSut.vardump(d1));
    }
}
