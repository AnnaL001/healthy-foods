package com.anna.healthyfoods;

import android.content.Intent;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.anna.healthyfoods.models.Settings;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityInstrumentationTest {
  public static Intent initializeIntent(){
    Intent intent = new Intent(ApplicationProvider.getApplicationContext(), HomeActivity.class);

    // Create lists of selected diets and preferences
    List<String> selectedDiets = new ArrayList<>(Collections.singletonList("high-protein"));
    List<String> selectedPreferences = new ArrayList<>(Collections.singletonList("gluten-free"));

    // Populate userSettings with name, selected diets and selected meal preferences
    Settings userSettings = new Settings("Anna", selectedDiets, selectedPreferences);

    intent.putExtra("userSettings", Parcels.wrap(userSettings));
    return intent;
  }
  
  @Rule
  public ActivityScenarioRule<HomeActivity> homeActivityRule = new ActivityScenarioRule<>(initializeIntent());
  View mealTypeListDecorView;

  @Before
  public void setUp()  {
    homeActivityRule.getScenario().onActivity(activity -> mealTypeListDecorView = activity.getWindow().getDecorView());
  }

  @Test
  public void launchHomeScreen_displaysTabLayout() {
    onView(withId(R.id.tab_layout)).perform(click()).check(matches(isDisplayed()));
  }

  @Test
  public void clickSearchTab_swipesToSearchFragment() {
    onView(allOf(withText((R.string.search)), isDescendantOfA(withId(R.id.tab_layout)))).perform(click());
    onView(withId(R.id.search_view)).check(matches(isDisplayed()));
  }

  @Test
  public void clickStarredTab_swipesToStarredFragment() {
    onView(allOf(withText(getInstrumentation().getTargetContext().getString(R.string.starred)), isDescendantOfA(withId(R.id.tab_layout)))).perform(click());
    onView(withText(R.string.no_starred_recipes)).check(matches(isDisplayed()));
  }

  @Test
  public void clickGridItem_displaysRecipeListOfThatCategory() {
    Intents.init();
    onView(withId(R.id.meal_type_grid)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    Intents.intended(hasComponent(RecipeListActivity.class.getName()));
  }
}
