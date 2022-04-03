package com.example.onlinebartertrading;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.onlinebartertrading.entities.User;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProfileActivityEspressoTest {

    static Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ProfileActivity.class);
    static Bundle bundle = new Bundle();
    static User user = new User("alex@email.com");
    static {
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
    }
    @Rule
    public ActivityScenarioRule<ProfileActivity> activityScenarioRule = new ActivityScenarioRule<>(intent);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void setEmail() {
        onView(withId(R.id.username)).check(matches(withText("alex@email.com")));
    }

    @Test
    public void setValue() {
        onView(withId(R.id.valueBox)).check(matches(withText("$1501")));
    }

    @Test
    public void setNumPosts() {
        onView(withId(R.id.postBox)).check(matches(withText("2 posts")));
    }
}