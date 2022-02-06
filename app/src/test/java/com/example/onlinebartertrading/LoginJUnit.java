package com.example.onlinebartertrading;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginJUnit {

    static AuthActivity mainActivity;

    @BeforeClass
    public static void setup() {
        mainActivity = new AuthActivity();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfEmailFieldIsEmpty() {
        assertTrue(AuthActivity.isEmptyEmail(""));
        assertFalse(AuthActivity.isEmptyEmail("testingemail@gmail.com"));
    }

    @Test
    public void checkIfEmailIsAppropriate() {
        assertTrue(AuthActivity.isValidEmail("testingemail@gmail.com"));
    }

    @Test
    public void checkIfEmailIsInappropriate() {
        assertTrue(AuthActivity.isValidEmail("testingemail.gmail.com"));
    }


}
