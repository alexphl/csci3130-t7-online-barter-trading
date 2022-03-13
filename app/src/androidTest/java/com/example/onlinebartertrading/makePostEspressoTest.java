package com.example.onlinebartertrading;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.onlinebartertrading.MakePostActivity.maxDescLength;
import static com.example.onlinebartertrading.MakePostActivity.maxTitleLength;
import static com.example.onlinebartertrading.MakePostActivity.maxValue;
import static org.junit.Assert.assertEquals;


import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class makePostEspressoTest {

    static Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MakePostActivity.class);
    static Bundle bundle = new Bundle();
    static {
        bundle.putString("userEmail", "alex@email.com");
        intent.putExtras(bundle);
    }
    @Rule
    public ActivityScenarioRule<MakePostActivity> activityScenarioRule = new ActivityScenarioRule<>(intent);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfMakePostIsVisible(){
        onView(withId(R.id.postTitle)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.postDesc)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.postValue)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfPostIsEmpty() {
        onView(withId(R.id.postTitle)).perform(typeText(""));
        onView(withId(R.id.postDesc)).perform(typeText("Test description"));
        onView(withId(R.id.postValue)).perform(typeText("123"));
        onView(withId(R.id.makePostButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_TITLE)));
    }

    @Test
    public void checkIfPostIsTooLong() {
        String longTitle = "";
        for (int i=0; i<maxTitleLength+1; i++){
            longTitle+="1";
        }
        onView(withId(R.id.postTitle)).perform(typeText(longTitle));
        onView(withId(R.id.postDesc)).perform(typeText("valid desc"));
        onView(withId(R.id.postValue)).perform(typeText("123"));
        onView(withId(R.id.makePostButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_TITLE)));
    }

    @Test
    public void checkIfPostIsValid() {
        onView(withId(R.id.postTitle)).perform(typeText("This is a valid title"));
        onView(withId(R.id.postDesc)).perform(typeText("Test description"));
        onView(withId(R.id.postValue)).perform(typeText("123"));
        onView(withId(R.id.makePostButton)).perform(click());
    }

    @Test
    public void checkIfValueIsZero(){
        onView(withId(R.id.postTitle)).perform(typeText("valid title"));
        onView(withId(R.id.postDesc)).perform(typeText("valid description"));
        onView(withId(R.id.postValue)).perform(typeText("0"));
        onView(withId(R.id.makePostButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_VALUE)));
    }

    @Test
    public void checkIfValueIsTooBig(){
        onView(withId(R.id.postTitle)).perform(typeText("valid title"));
        onView(withId(R.id.postDesc)).perform(typeText("valid description"));
        onView(withId(R.id.postValue)).perform(typeText(String.valueOf(maxValue+1)));
        onView(withId(R.id.makePostButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_VALUE)));
    }


}
