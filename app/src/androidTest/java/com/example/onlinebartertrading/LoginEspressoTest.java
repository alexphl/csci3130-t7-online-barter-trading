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
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginEspressoTest {
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

    /*** User Acceptance Test - II**/
    @Test
    public void checkIfEmailFieldIsEmpty() {
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        onView(withId(R.id.emailFieldL)).perform(typeText(""));
        onView(withId(R.id.passwordFieldL)).perform(typeText("testingPassword"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.EMPTY_EMAIL)));
    }

    /*** User Acceptance Test - III**/
    @Test
    public void checkIfPasswordFieldIsEmpty() {
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        onView(withId(R.id.emailFieldL)).perform(typeText("testemail@gmail.com"));
        onView(withId(R.id.passwordFieldL)).perform(typeText(""));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.EMPTY_PASSWORD)));
    }

    /*** User Acceptance Test - IV**/
    @Test
    public void checkIfEmailDoesNotMatch() {
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        onView(withId(R.id.emailFieldL)).perform(typeText("alex@email.comm"));
        onView(withId(R.id.passwordFieldL)).perform(typeText("a"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.INVALID_EMAIL)));
    }

    /*** User Acceptance Test - V**/
    @Test
    public void checkIfPasswordMatches() {
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        onView(withId(R.id.emailFieldL)).perform(typeText("alex@email.com"));
        onView(withId(R.id.passwordFieldL)).perform(typeText("aa"));
        onView(withId(R.id.loginButton)).perform(click());
        intended(hasComponent(RoleDecisionActivity.class.getName()));
    }

    /*** User Acceptance Test - VI**/
    @Test
    public void checkIfPasswordDoesNotMatch() {
        onView(withId(R.id.viewPager)).perform(swipeLeft());
        onView(withId(R.id.emailFieldL)).perform(typeText("alex@email.com"));
        onView(withId(R.id.passwordFieldL)).perform(typeText("aaa"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.INVALID_PASSWORD)));
    }
}