package ru.eu.webservices;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ru.eu.webservices.generated.City;
import ru.eu.webservices.generated.FlightsWS_REST;

/**
 * Unit test for simple TestRSClient.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        //assertTrue(true);
        for(City c : FlightsWS_REST.flights().cities().getAsCityList().getCity()) {
            System.out.println(c.getName());
        }
    }
}
