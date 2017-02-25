package net.workingdeveloper.java.vardump.test;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Christoph Graupner on 2017-01-22.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
@RunWith(DataProviderRunner.class)
public class VarDumpMatcherTest {

    @DataProvider
    public static Object[][] dataProviderVardumpArray() {
        return new Object[][]{
                {
                        "Test@0000",
                        "Test@0000",
                        true
                }, {
                        "Test@0000",
                        "Test@75828a0f",
                        true
                }, {
                        "Test@0000",
                        "Test2@75828a0f",
                        false
                }
        };
    }

    @Test
    @UseDataProvider("dataProviderVardumpArray")
    public void matchesSafely(String matcher, String actual, boolean expected) throws Exception {
        VarDumpMatcher lSUT = new VarDumpMatcher(matcher);
        assertThat(lSUT.matchesSafely(actual), is(expected));
    }
}
