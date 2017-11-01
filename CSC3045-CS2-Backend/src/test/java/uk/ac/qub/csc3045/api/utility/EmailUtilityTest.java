package uk.ac.qub.csc3045.api.utility;

import org.junit.Test;

public class EmailUtilityTest {

    @Test(expected = RuntimeException.class)
    public void invalidEmailRecipientsTest() {
        EmailUtility.sendEmail("notanemail",
                "Integration Test",
                "This is meant to be a failing test so...");
    }
}
