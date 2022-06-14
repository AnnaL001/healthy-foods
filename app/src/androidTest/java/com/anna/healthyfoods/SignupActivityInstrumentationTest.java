package com.anna.healthyfoods;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.anna.healthyfoods.custom_matcher.TextInputLayoutMatcher.hasDisplayedErrorText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupActivityInstrumentationTest {
  @Rule
  public ActivityScenarioRule<SignupActivity> signUpActivityRule = new ActivityScenarioRule<>(SignupActivity.class);

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

    sleep(500);

    // Type valid confirm password
    onView(allOf(
            isDescendantOfA(withId(R.id.confirm_password_text_input_layout)),
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

    sleep(500);

    onView(withId(R.id.btn_signup)).perform(click());
    onView(withId(R.id.email_text_input_layout)).check(matches(hasDisplayedErrorText("Please enter a valid email address")));
  }

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

    onView(withId(R.id.btn_signup)).perform(click());
    onView(withId(R.id.password_text_input_layout)).check(matches(hasDisplayedErrorText("Password should have at least 8 characters with uppercase, lowercase, digits and special characters")));
  }

  @Test
  public void typeInvalidConfirmPassword_displaysErrorMessage() {
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

    sleep(500);

    // Type invalid confirm password
    onView(allOf(
            isDescendantOfA(withId(R.id.confirm_password_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("12345678")).check(matches(withText("12345678"))).perform(closeSoftKeyboard());

    sleep(500);

    onView(withId(R.id.btn_signup)).perform(click());
    onView(withId(R.id.confirm_password_text_input_layout)).check(matches(hasDisplayedErrorText("Confirm password should match password")));
  }

  @Test
  public void clickButton_redirectsToLoginAfterSignup() {
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

    sleep(500);

    // Type valid confirm password
    onView(allOf(
            isDescendantOfA(withId(R.id.confirm_password_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("Lanna001$")).check(matches(withText("Lanna001$"))).perform(closeSoftKeyboard());

    Intents.init();
    onView(withId(R.id.btn_signup)).perform(click());

    // Sleep while user is signed up
    sleep(2000);

    // Confirm user is redirected to login
    Intents.intended(hasComponent(LoginActivity.class.getName()));
  }

  private void sleep(int milliseconds){
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e){
      System.out.println("Got interrupted!");
    }
  }

}