package com.anna.healthyfoods;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RecipeListActivityInstrumentationTest {
  @Rule
  public ActivityScenarioRule<RecipeListActivity> recipeListActivityRule = new ActivityScenarioRule<>(RecipeListActivity.class);

  private View recipeListDecorView;

  @Before
  public void setUp() {
    recipeListActivityRule.getScenario().onActivity(activity -> recipeListDecorView = activity.getWindow().getDecorView());
  }

  @Test
  public void clickListItem_displaysToastWithCorrectRecipeTitle() {
    String recipeTitle = "1. Chicken Vesuvio";
    onData(anything())
            .inAdapterView(withId(R.id.recipe_list_view))
            .atPosition(0)
            .perform(click());

    onView(withText(recipeTitle)).inRoot(withDecorView(not(recipeListDecorView)))
            .check(matches(withText(recipeTitle)));
  }
}