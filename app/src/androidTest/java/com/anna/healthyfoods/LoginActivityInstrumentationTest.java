package com.anna.healthyfoods;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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

  // Test only passes if user is not currently logged in
  @Test
  public void typeIntoEditText_validateEditText() {
    // Type valid email address
    onView(allOf(
            isDescendantOfA(withId(R.id.email_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("anna@gmail.com")).check(matches(withText("anna@gmail.com"))).perform(closeSoftKeyboard());

    sleep(500);

    // Type valid password
    onView(allOf(
            isDescendantOfA(withId(R.id.password_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("Lanna001$")).check(matches(withText("Lanna001$"))).perform(closeSoftKeyboard());
  }

  // Test only passes if user is not currently logged in
  @Test
  public void typeInvalidEmail_displaysErrorMessage() {
    // Type invalid email address
    onView(allOf(
            isDescendantOfA(withId(R.id.email_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("anna")).check(matches(withText("anna"))).perform(closeSoftKeyboard());

    sleep(500);

    onView(withId(R.id.btn_login)).perform(click());
    onView(withId(R.id.email_text_input_layout)).check(matches(hasDisplayedErrorText("Please enter a valid email address")));
  }

  // Test only passes if user is not currently logged in
  @Test
  public void typeInvalidPassword_displaysErrorMessage() {
    // Type valid email address
    onView(allOf(
            isDescendantOfA(withId(R.id.email_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("anna@gmail.com")).check(matches(withText("anna@gmail.com"))).perform(closeSoftKeyboard());

    sleep(500);

    // Type invalid password
    onView(allOf(
            isDescendantOfA(withId(R.id.password_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("12345678")).check(matches(withText("12345678"))).perform(closeSoftKeyboard());

    sleep(500);

    onView(withId(R.id.btn_login)).perform(click());
    onView(withId(R.id.password_text_input_layout)).check(matches(hasDisplayedErrorText("Password should have at least 8 characters with uppercase, lowercase, digits and special characters")));
  }

  // Test only passes if user is not currently logged in
  @Test
  public void clickingButton_verificationToastDisplaysIfNotVerified() {
    String emailAddress = "anna@gmail.com";
    String password = "Lanna001$";

    // Type valid email address
    onView(allOf(
            isDescendantOfA(withId(R.id.email_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText(emailAddress)).check(matches(withText(emailAddress))).perform(closeSoftKeyboard());

    sleep(500);

    // Type valid password
    onView(allOf(
            isDescendantOfA(withId(R.id.password_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText(password)).check(matches(withText(password))).perform(closeSoftKeyboard());

    sleep(500);

    onView(withId(R.id.btn_login)).perform(click());
    onView(withText(R.string.email_verification_prompt)).inRoot(withDecorView(not(activityDecorView))).check(matches(withText(R.string.email_verification_prompt)));
  }

  // Test only passes if user is not currently logged in
  @Test
  public void clickingButton_redirectToHomeAfterLoginIfVerified() {
    String emailAddress = "lynnanastasia83@gmail.com";
    String password = "Lanna001$";

    // Type valid email address
    onView(allOf(
            isDescendantOfA(withId(R.id.email_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText(emailAddress)).check(matches(withText(emailAddress))).perform(closeSoftKeyboard());

    sleep(500);

    // Type valid password
    onView(allOf(
            isDescendantOfA(withId(R.id.password_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText(password)).check(matches(withText(password))).perform(closeSoftKeyboard());

    sleep(500);

    onView(withId(R.id.btn_login)).perform(click());

    sleep(5000);

    onView(withId(R.id.tab_layout)).check(matches(isDisplayed()));
  }

  // TO BE CHANGED: USE ESPRESSO IDLING RESOURCES
  private void sleep(int milliseconds){
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e){
      System.out.println("Got interrupted!");
    }
  }
}