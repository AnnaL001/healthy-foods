package com.anna.healthyfoods;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityInstrumentationTest {
  @Rule
  public ActivityScenarioRule<HomeActivity> homeActivityRule = new ActivityScenarioRule<>(HomeActivity.class);
  View mealTypeListDecorView;

  @Before
  public void setUp()  {
    homeActivityRule.getScenario().onActivity(activity -> mealTypeListDecorView = activity.getWindow().getDecorView());
  }

  // Test only passes if user is logged in
  @Test
  public void launchHomeScreen_displaysTabLayout() {
    onView(withId(R.id.tab_layout)).perform(click()).check(matches(isDisplayed()));
  }

  // Test only passes if user is logged in
  @Test
  public void clickSearchTab_swipesToSearchFragment() {
    // Sleep while settings are fetched from Firebase
    sleep();

    onView(allOf(withText(getInstrumentation().getTargetContext().getString(R.string.search)), isDescendantOfA(withId(R.id.tab_layout)))).perform(click());
    onView(withId(R.id.search_view)).check(matches(isDisplayed()));
  }

  // Test only passes if user is logged in
  @Test
  public void clickStarredTab_swipesToStarredFragment() {
    // Sleep while settings are fetched from Firebase
    sleep();

    onView(allOf(withText(getInstrumentation().getTargetContext().getString(R.string.saved)), isDescendantOfA(withId(R.id.tab_layout)))).perform(click());

    // Sleep while saved recipes are fetched from Firebase
    sleep();

    onView(withId(R.id.starred_recipe_list)).check(matches(isDisplayed()));
  }

  // Test only passes if user is logged in
  @Test
  public void clickGridItem_displaysRecipeListOfThatCategory() {
    Intents.init();

    // Sleep as settings are fetched from Firebase
    sleep();

    onView(withId(R.id.meal_type_grid)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    // Confirm launch of category's recipe list when grid item is clicked
    Intents.intended(hasComponent(RecipeListActivity.class.getName()));
    onView(withText(R.string.breakfast)).check(matches(isDisplayed()));
  }

  // Test only passes if user is logged in
  @Test
  public void clickLogout_redirectsToLaunchScreen() {
    Intents.init();

    // Sleep as settings are fetched from Firebase
    sleep();
    onView(withId(R.id.action_logout)).perform(click());

    // Sleep as user is logged out
    sleep();
    onView(withId(R.id.btn_get_started)).check(matches(isDisplayed()));
  }

  // TO BE CHANGED: USE ESPRESSO IDLING RESOURCES
  private void sleep(){
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e){
      System.out.println("Got interrupted!");
    }
  }
}
