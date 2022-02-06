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
}
