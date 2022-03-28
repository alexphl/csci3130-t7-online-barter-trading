package com.example.onlinebartertrading;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.onlinebartertrading.entities.User;

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
public class PostListActivityEspressoTest {

    static Intent intent = new Intent(ApplicationProvider.getApplicationContext(), PostListActivity.class);
    static Bundle bundle = new Bundle();
    static User user = new User("testuser1@email.com");
    static {
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
    }
    @Rule
    public ActivityScenarioRule<PreferenceActivity> activityScenarioRule = new ActivityScenarioRule<>(intent);

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
    public void checkIfFilterButtonWorks() {
        onView(withId(R.id.setting)).perform(click());
        intended(hasComponent(PreferenceActivity.class.getName()));
    }

    /*** User Acceptance Test - II**/
    @Test
    public void checkIfPostIsClickable() {
        onView(withId(R.id.listView)).perform(click());
        onView(withId(R.id.dialog)).check(matches(isDisplayed()));
    }

    /*** User Acceptance Test - III**/
    @Test
    public void checkIfPostDetailsIsDisplayed() {
        onView(withId(R.id.listView)).perform(click());
        onView(withText("Submit")).perform(click());
        onView(withId(R.id.dialog)).check(matches(isDisplayed()));
        onView(withId(R.id.receiver_item)).perform(typeText("item"));
        onView(withText("Submit")).perform(click());
        onView(withId(R.id.dialog)).check(matches(isDisplayed()));
    }

    /*** User Acceptance Test - IV**/
    @Test
    public void checkIfDialogFieldIsNotEmpty() {
        onView(withId(R.id.listView)).perform(click());
        onView(withId(R.id.dialog)).check(matches(isDisplayed()));
    }

}
