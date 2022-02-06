package com.example.onlinebartertrading;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

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
        onView(withId(R.id.emailField).perform(typeText("testemail@gmail.com")));
        onView(withId(R.id.loginBttn)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.EMPTY_FIELD)));
    }

}
