package com.anna.healthyfoods;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.anna.healthyfoods.custom_matcher.TextInputLayoutMatcher.hasDisplayedErrorText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityInstrumentationTest {
  @Rule
  public ActivityScenarioRule<LoginActivity> loginActivityRule = new ActivityScenarioRule<>(LoginActivity.class);
  private View activityDecorView;

  @Before
  public void setUp() {
    loginActivityRule.getScenario().onActivity(activity -> activityDecorView = activity.getWindow().getDecorView());
  }

  @Test
  public void typeIntoEditText_validateEditText() {
    // Type valid email address
    onView(allOf(
            isDescendantOfA(withId(R.id.email_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("anna@gmail.com")).check(matches(withText("anna@gmail.com"))).perform(closeSoftKeyboard());

    sleep();

    // Type valid password
    onView(allOf(
            isDescendantOfA(withId(R.id.password_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("Lanna001$")).check(matches(withText("Lanna001$"))).perform(closeSoftKeyboard());
  }

  @Test
  public void typeInvalidEmail_displaysErrorMessage() {
    // Type invalid email address
    onView(allOf(
            isDescendantOfA(withId(R.id.email_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("anna")).check(matches(withText("anna"))).perform(closeSoftKeyboard());

    sleep();

    onView(withId(R.id.btn_login)).perform(click());
    onView(withId(R.id.email_text_input_layout)).check(matches(hasDisplayedErrorText("Please enter a valid email address")));
  }

  @Test
  public void typeInvalidPassword_displaysErrorMessage() {
    // Type valid email address
    onView(allOf(
            isDescendantOfA(withId(R.id.email_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("anna@gmail.com")).check(matches(withText("anna@gmail.com"))).perform(closeSoftKeyboard());

    sleep();

    // Type invalid password
    onView(allOf(
            isDescendantOfA(withId(R.id.password_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("12345678")).check(matches(withText("12345678"))).perform(closeSoftKeyboard());

    sleep();

    onView(withId(R.id.btn_login)).perform(click());
    onView(withId(R.id.password_text_input_layout)).check(matches(hasDisplayedErrorText("Password should have at least 8 characters with uppercase, lowercase, digits and special characters")));
  }

  @Test
  public void clickingButton_verificationToastDisplaysIfNotVerified() {
    // Type valid email address
    onView(allOf(
            isDescendantOfA(withId(R.id.email_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("anna@gmail.com")).check(matches(withText("anna@gmail.com"))).perform(closeSoftKeyboard());

    sleep();

    // Type valid password
    onView(allOf(
            isDescendantOfA(withId(R.id.password_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("Lanna001$")).check(matches(withText("Lanna001$"))).perform(closeSoftKeyboard());

    sleep();

    onView(withId(R.id.btn_login)).perform(click());
    onView(withText(R.string.email_verification_prompt)).inRoot(withDecorView(not(activityDecorView))).check(matches(withText(R.string.email_verification_prompt)));
  }

  private void sleep(){
    try {
      Thread.sleep(500);
    } catch (InterruptedException e){
      System.out.println("Got interrupted!");
    }
  }
}