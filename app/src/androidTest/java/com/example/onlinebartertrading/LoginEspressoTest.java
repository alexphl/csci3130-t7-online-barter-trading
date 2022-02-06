package com.example.onlinebartertrading;

import android.content.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;
import androidx.test.espresso.*;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginEspressoTest {

    @Rule
    public ActivityScenarioRule<AuthActivity> myRule = new ActivityScenarioRule<>(AuthActivity.class);
    public IntentsTestRule<AuthActivity> myIntentRule = new IntentsTestRule<>(AuthActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }
<<<<<<< HEAD

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrading", appContext.getPackageName());
    }

    @Test
    public void checkIfLoginPageIsVisible() {
        onView(withId(R.id.emailField).check(matches(withText(R.string.VALID_EMAIL))));
        onView(withId(R.id.passwordField).check(matches(withText(R.string.VALID_PASSWORD))));
    }

    @Test
    public void checkIfEmailFieldIsEmpty() {
        onView(withId(R.id.emailField).perform(typeText("")));
<<<<<<< HEAD
        onView(withId(R.id.passwordField).perform(typeText("testingPassword")));
        onView(withId(R.id.loginBttn)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.EMPTY_EMAIL)));
    }

    @Test
    public void checkIfPasswordFieldIsEmpty() {
        onView(withId(R.id.emailField).perform(typeText("testemail@gmail.com")));
        onView(withId(R.id.passwordField).perform(typeText("")));
        onView(withId(R.id.loginBttn)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.EMPTY_PASSWORD)));
    }

    @Test
    public void checkIfEmailIsAppropriate() {
=======
>>>>>>> parent of 27a815b (Fixed previous Espresso Test and added test to determine if login email is valid or not)
        onView(withId(R.id.emailField).perform(typeText("testemail@gmail.com")));
        onView(withId(R.id.loginBttn)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.EMPTY_FIELD)));
    }

=======
>>>>>>> parent of ca2ad90 (Added Espresso Test to check if the login page is visible)
}
