package com.example.onlinebartertrading;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.annotation.Target;
import java.security.NoSuchAlgorithmException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SignupUnitTests {
    private static SignupFormFragment signupFragment;

    @BeforeClass
    public static void setup() {
        signupFragment= new SignupFormFragment();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfNameIsEmpty() {
        assertTrue(signupFragment.isEmptyName("", "lname"));
        assertTrue(signupFragment.isEmptyName("fname", ""));
        assertFalse(signupFragment.isEmptyName("fname", "lname"));
    }

    @Test
    public void checkIfEmailIsValid() {
        assertTrue(signupFragment.isValidEmailAddress("email@test.com"));
        assertFalse(signupFragment.isValidEmailAddress("email"));
    }



}
