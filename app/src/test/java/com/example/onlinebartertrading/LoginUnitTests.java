package com.example.onlinebartertrading;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.onlinebartertrading.lib.DBHandler;

import java.security.NoSuchAlgorithmException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginUnitTests {
    static LoginFormFragment loginActivity;

    @BeforeClass
    public static void setup() {
        loginActivity = new LoginFormFragment();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /*** User Acceptance Test - I**/
    @Test
    public void checkIfEmailFieldIsEmpty() {
        assertTrue(loginActivity.isEmptyEmail(""));
        assertFalse(loginActivity.isEmptyEmail("xyz@dal.ca"));
    }

    /*** User Acceptance Test - II**/
    @Test
    public void checkIfPasswordFieldIsEmpty() {
        assertTrue(loginActivity.isEmptyPassword(""));
        assertFalse(loginActivity.isEmptyPassword("testingPassword"));

    }

    /*** User Acceptance Test - III**/
    @Test
    public void checkIfPasswordHashed() {
        try {
            assertTrue(loginActivity.getPasswordHash("testPassword").equals(DBHandler.hashString("testPassword")));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Password has not hashed correctly");
        }
    }

    /*** User Acceptance Test - IV**/
    @Test
    public void checkIfPasswordNotHashed() {
        try {
            assertFalse(loginActivity.getPasswordHash("testPassword").equals(DBHandler.hashString("testPasswordWrong")));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Password has not hashed correctly");
        }
    }
}