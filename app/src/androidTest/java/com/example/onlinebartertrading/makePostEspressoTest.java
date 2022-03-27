package com.example.onlinebartertrading;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.onlinebartertrading.MakePostActivity.maxTitleLength;
import static com.example.onlinebartertrading.MakePostActivity.maxValue;


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

@RunWith(AndroidJUnit4.class)
public class makePostEspressoTest {

    static Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MakePostActivity.class);
    static Bundle bundle = new Bundle();
    static User user = new User("a");
    static {
        bundle.putSerializable("user", user);
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
        onView(withId(R.id.postDesc)).perform(replaceText("©Test description"));
        onView(withId(R.id.postValue)).perform(typeText("123"), closeSoftKeyboard());
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
        onView(withId(R.id.postDesc)).perform(replaceText("©valid desc"));
        onView(withId(R.id.postValue)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.makePostButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_TITLE)));
    }

    @Test
    public void checkIfValueIsZero(){
        onView(withId(R.id.postTitle)).perform(typeText("valid title"));
        onView(withId(R.id.postDesc)).perform(replaceText("©valid description"));
        onView(withId(R.id.postValue)).perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.makePostButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_VALUE)));
    }

    @Test
    public void checkIfValueIsTooBig(){
        onView(withId(R.id.postTitle)).perform(typeText("valid title"));
        onView(withId(R.id.postDesc)).perform(replaceText("©valid description"));
        onView(withId(R.id.postValue)).perform(typeText(String.valueOf(maxValue+1)), closeSoftKeyboard());
        onView(withId(R.id.makePostButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_VALUE)));
    }


}
