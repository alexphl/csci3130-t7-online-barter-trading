package com.example.onlinebartertrading;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignupEspressoTests {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.onlinebartertrading", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<AuthActivity> myRule = new ActivityScenarioRule<>(AuthActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    /*** User Acceptance Test - I**/
    @Test
    public void checkIfSignupPageIsVisible() {
        // For login
        // onView(withId(R.id.viewPager)).perform(swipeLeft());
        onView(withId(R.id.fnameField)).check(matches(withText("")));
        onView(withId(R.id.lnameField)).check(matches(withText("")));
        onView(withId(R.id.emailField)).check(matches(withText("")));
        onView(withId(R.id.passwordField)).check(matches(withText("")));
        onView(withId(R.id.passwordConfirmField)).check(matches(withText("")));
    }

    /*** User Acceptance Test - II**/
    @Test
    public void checkIfNameIsEmpty() {
        onView(withId(R.id.emailField)).perform(typeText("testemail@gmail.com"));
        onView(withId(R.id.passwordField)).perform(typeText("password"));
        onView(withId(R.id.passwordConfirmField)).perform(typeText("password"));
        onView(withId(R.id.fnameField)).perform(typeText(""));
        onView(withId(R.id.lnameField)).perform(typeText("Ha"));
        onView(withId(R.id.signupSubmitButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.emptyName)));
        onView(withId(R.id.fnameField)).perform(typeText("Ha"));
        onView(withId(R.id.lnameField)).perform(replaceText(""));
        onView(withId(R.id.signupSubmitButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.emptyName)));
    }

    /*** User Acceptance Test - III**/
    @Test
    public void checkIfEmailIsEmptyOrInvalid() {
        onView(withId(R.id.fnameField)).perform(typeText("Ha"));
        onView(withId(R.id.lnameField)).perform(typeText("Ha"));
        onView(withId(R.id.emailField)).perform(typeText(""));
        onView(withId(R.id.passwordField)).perform(typeText("password"));
        onView(withId(R.id.signupSubmitButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.emptyEmail)));
        onView(withId(R.id.emailField)).perform(replaceText("email"));
        onView(withId(R.id.signupSubmitButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.invalidEmail)));
    }

    /*** User Acceptance Test - IV**/
    @Test
    public void checkIfPasswordIsEmpty() {
        onView(withId(R.id.fnameField)).perform(typeText("Ha"));
        onView(withId(R.id.lnameField)).perform(typeText("Ha"));
        onView(withId(R.id.emailField)).perform(typeText("testemail@gmail.com"));
        onView(withId(R.id.passwordField)).perform(typeText(""));
        onView(withId(R.id.signupSubmitButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.emptyPassword)));
        onView(withId(R.id.passwordField)).perform(replaceText("password"));
        onView(withId(R.id.passwordConfirmField)).perform(typeText(""));
        onView(withId(R.id.signupSubmitButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.diffPassword)));
    }

    /*** User Acceptance Test - V**/
    @Test
    public void checkIfFragSlide() {
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        onView(withId(R.id.emailField)).check(matches(withText("")));
        onView(withId(R.id.passwordField)).check(matches(withText("")));
    }
}