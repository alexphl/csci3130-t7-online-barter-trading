package com.example.onlinebartertrading;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.integration.JMock1Adapter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class preferenceEspressoTest {


    @Rule
    public ActivityScenarioRule<PreferenceActivity> myRule = new ActivityScenarioRule<>(PreferenceActivity.class);



    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void checkIfPreferenceIsVisible() {
        onView(withId(R.id.carChip)).check(matches(isDisplayed()));

    }

    @Test
    public void checkIfMinValueIsEmpty() {
        onView(withId(R.id.maxValue)).perform(typeText("100"));
        onView(withId(R.id.carChip)).perform(click());
        onView(withId(R.id.twentyFiveDist)).perform(click());
        onView(withId(R.id.preferenceButton)).perform(click());
        onView(withId(R.id.prefStatusLabel)).check(matches(withText(R.string.EMPTY_FIELD)));
    }

    @Test
    public void checkIfMaxValueIsEmpty() {
        onView(withId(R.id.minValue)).perform(typeText("100"));
        onView(withId(R.id.carChip)).perform(click());
        onView(withId(R.id.twentyFiveDist)).perform(click());
        onView(withId(R.id.preferenceButton)).perform(click());
        onView(withId(R.id.prefStatusLabel)).check(matches(withText(R.string.EMPTY_FIELD)));
    }

    @Test
    public void minLessThanMax(){
        onView(withId(R.id.minValue)).perform(typeText("100"));
        onView(withId(R.id.maxValue)).perform(typeText("10"));
        onView(withId(R.id.twentyFiveDist)).perform(click());
        onView(withId(R.id.preferenceButton)).perform(click());
        onView(withId(R.id.prefStatusLabel)).check(matches(withText(R.string.MIN_LESS_MAX)));
    }


    @Test
    public void checkIFSetMinValueTrue() {
        onView(withId(R.id.minValue)).perform(typeText("0"));
        onView(withId(R.id.maxValue)).perform(typeText("99"));
        onView(withId(R.id.twentyFiveDist)).perform(click());
        onView(withId(R.id.preferenceButton)).perform(click());
        onView(withId(R.id.prefStatusLabel)).check(matches(withText("")));
    }

    @Test
    public void checkIFSetMinValueFalse() {
        onView(withId(R.id.minValue)).perform(typeText("0"));
        onView(withId(R.id.maxValue)).perform(typeText("9999999999999"));
        onView(withId(R.id.twentyFiveDist)).perform(click());
        onView(withId(R.id.preferenceButton)).perform(click());
        onView(withId(R.id.prefStatusLabel)).check(matches(withText(R.string.EMPTY_FIELD)));
    }

    @Test
    public void checkIFSetMaxValueFalse() {
        onView(withId(R.id.minValue)).perform(typeText("0"));
        onView(withId(R.id.maxValue)).perform(typeText("999999999999"));
        onView(withId(R.id.twentyFiveDist)).perform(click());
        onView(withId(R.id.preferenceButton)).perform(click());
        onView(withId(R.id.prefStatusLabel)).check(matches(withText(R.string.EMPTY_FIELD)));
    }

    @Test
    public void checkIFSetMaxValueTrue() {
        onView(withId(R.id.minValue)).perform(typeText("0"));
        onView(withId(R.id.maxValue)).perform(typeText("1000"));
        onView(withId(R.id.twentyFiveDist)).perform(click());
        onView(withId(R.id.preferenceButton)).perform(click());
        onView(withId(R.id.prefStatusLabel)).check(matches(withText("")));
    }

    @Test
    public void checkIFSetDistance() {
        onView(withId(R.id.minValue)).perform(typeText("0"));
        onView(withId(R.id.maxValue)).perform(typeText("1000"));
        onView(withId(R.id.twentyFiveDist)).perform(click());
        onView(withId(R.id.preferenceButton)).perform(click());
        onView(withId(R.id.prefStatusLabel)).check(matches(withText("")));
    }

}

