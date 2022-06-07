package com.anna.healthyfoods;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

import android.content.Context;
import android.content.res.Resources;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserDetailsActivityInstrumentationTest {
  @Rule
  public ActivityScenarioRule<UserDetailsActivity> userDetailsActivityRule = new ActivityScenarioRule<>(UserDetailsActivity.class);

  private Resources resources;
  private Context context;

  @Before
  public void setUp()  {
    resources = getInstrumentation().getTargetContext().getResources();
    context = getInstrumentation().getContext();
  }

  @Test
  public void typeIntoEditText_validateEditText() {
    onView(allOf(
            isDescendantOfA(withId(R.id.name_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("Anna")).check(matches(withText("Anna")));
  }

  @Test
  public void selectChips_validateChipGroups() {
    onView(allOf(
            isDescendantOfA(withId(R.id.diet_chip_group)),
            withId(R.id.high_protein_diet))
    ).perform(click()).check(matches(isChecked()));

    onView(allOf(
            isDescendantOfA(withId(R.id.allergy_chip_group)),
            withId(R.id.gluten_free)
    )).perform(click()).check(matches(isChecked()));
  }

  @Test
  public void initializeButton_nameIsSentToMealTypesActivity() {
    onView(withId(R.id.name_text_input_layout)).perform(swipeUp());

    // Enter name
    onView(allOf(
            isDescendantOfA(withId(R.id.name_text_input_layout)),
            withClassName(endsWith("TextInputEditText"))
    )).perform(typeText("Anna")).check(matches(withText("Anna"))).perform(closeSoftKeyboard());

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e){
      System.out.println("Got interrupted!");
    }

    // Select diet
    onView(allOf(
            isDescendantOfA(withId(R.id.diet_chip_group)),
            withId(R.id.high_protein_diet))
    ).perform(click()).check(matches(isChecked()));

    // Select allergies
    onView(allOf(
            isDescendantOfA(withId(R.id.allergy_chip_group)),
            withId(R.id.gluten_free)
    )).perform(click()).check(matches(isChecked()));

    onView(withId(R.id.btn_next)).perform(click());
    onView(withId(R.id.welcome_text)).check(matches(withText("Welcome Anna")));
  }
}

