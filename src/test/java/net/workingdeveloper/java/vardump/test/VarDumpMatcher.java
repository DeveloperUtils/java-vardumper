package net.workingdeveloper.java.vardump.test;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Christoph Graupner on 2016-12-26.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class VarDumpMatcher extends TypeSafeMatcher<String> {
    private static final String REPLACEMENT = "@00000000";
    private static final String UNIFIER     = "@[A-Fa-f0-9]{4,}\\b";
    final String fMatch;

    public VarDumpMatcher(final String aMatch) {
        fMatch = aMatch.replaceAll(UNIFIER, REPLACEMENT);
    }

    public static VarDumpMatcher matchesVarDump(final String aVarDump) {
        return new VarDumpMatcher(aVarDump);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(fMatch);
    }

    @Override
    protected boolean matchesSafely(String item) {
        return item.replaceAll(UNIFIER, REPLACEMENT).equals(fMatch);
    }

    @Override
    protected void describeMismatchSafely(String item, Description mismatchDescription) {
        super.describeMismatchSafely(item.replaceAll(UNIFIER, REPLACEMENT), mismatchDescription);
    }
}
