package net.workingdeveloper.java.vardump.impl.test_object;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class TestCyclic {
    TestCyclic fChild;
    TestCyclic fParent;

    public TestCyclic(TestCyclic aParent) {
        fParent = aParent;
        if (fParent != null) {
            fParent.fChild = this;
        }
    }
}
