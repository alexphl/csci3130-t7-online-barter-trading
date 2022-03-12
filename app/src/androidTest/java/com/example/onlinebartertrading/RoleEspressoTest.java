package com.example.onlinebartertrading;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import androidx.test.runner.AndroidJUnit4;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

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
@RunWith(AndroidJUnit4.class)
public class RoleEspressoTest {

    @Rule
    public ActivityScenarioRule<RoleDecisionActivity> myRule = new ActivityScenarioRule<>(RoleDecisionActivity.class);
    public IntentsTestRule<RoleDecisionActivity> myIntentRule = new IntentsTestRule<>(RoleDecisionActivity.class);

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
    public void checkIfProviderButtonWorks() {
        onView(withId(R.id.ProviderButton)).perform(click());
        intended(hasComponent(MakePostActivity.class.getName()));
    }

    /*** User Acceptance Test - I**/
    @Test
    public void checkIfReceiverButtonWorks() {
        onView(withId(R.id.ReceiverButton)).perform(click());
        intended(hasComponent(ShowDetailsActivity.class.getName()));
    }
}
