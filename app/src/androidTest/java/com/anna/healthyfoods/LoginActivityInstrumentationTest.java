package com.anna.healthyfoods;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.*;

import android.app.Activity;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityInstrumentationTest {
  @Rule
  public ActivityScenarioRule<LoginActivity> loginActivityRule = new ActivityScenarioRule<>(LoginActivity.class);

  @Test
  public void typeIntoEditText_validateEditText() {
    // Type valid email address
    onView(allOf(
            isDescendantOfA(withId(R.id.email_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("anna@gmail.com")).check(matches(withText("anna@gmail.com"))).perform(closeSoftKeyboard());

    try {
      Thread.sleep(500);
    } catch (InterruptedException e){
      System.out.println("Got interrupted!");
    }

    // Type valid password
    onView(allOf(
            isDescendantOfA(withId(R.id.password_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("Lanna001$")).check(matches(withText("Lanna001$"))).perform(closeSoftKeyboard());
  }
}