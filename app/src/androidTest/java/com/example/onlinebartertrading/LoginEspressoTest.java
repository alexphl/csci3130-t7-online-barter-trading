package com.example.onlinebartertrading;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static android.content.Intent.*;
import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        onView(withId(R.id.passwordField).perform(typeText("testingPassword")));
        onView(withId(R.id.loginBttn)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.EMPTY_EMAIL)));
    }

    @Test
    public void checkIfEmailIsAppropriate() {
        onView(withId(R.id.emailField).perform(typeText("testemail@gmail.com")));
        onView(withId(R.id.passwordField).perform(typeText("testingPassword")));
        onView(withId(R.id.loginBttn)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.DO_NOTHING)));
    }

    @Test
    public void checkIfEmailIsInappropriate() {
        onView(withId(R.id.emailField).perform(typeText("testemail.gmail.com")));
        onView(withId(R.id.passwordField).perform(typeText("testingPassword")));
        onView(withId(R.id.loginBttn)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.INVALID_EMAIL)));
    }

    @Test
    public void checkIfPasswordIsAppropriate() {
        onView(withId(R.id.emailField).perform(typeText("testemail@gmail.com")));
        onView(withId(R.id.passwordField).perform(typeText("testingPassword")));
        onView(withId(R.id.loginBttn)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.DO_NOTHING)));
    }

    @Test
    public void checkIfPasswordIsInappropriate() {
        onView(withId(R.id.emailField).perform(typeText("testemail@gmail.com")));
        onView(withId(R.id.passwordField).perform(typeText("test")));
        onView(withId(R.id.loginBttn)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.INVALID_PASSWORD)));
    }

    @Test
    public void checkIfMoved2HomePage() {
        onView(withId(R.id.emailField).perform(typeText("testemail@gmail.com")));
        onView(withId(R.id.passwordField).perform(typeText("testingPassword")));
        onView(withId(R.id.loginBttn)).perform(click());
        intended(hasComponent(makePostActivity.class.getName()));
    }
}
