package com.anna.healthyfoods;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.anything;

import android.view.View;

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
public class MealTypesActivityInstrumentationTest {
  @Rule
  public ActivityScenarioRule<MealTypesActivity> mealTypesActivityRule = new ActivityScenarioRule<>(MealTypesActivity.class);

  private View mealTypesDecorView;

  @Before
  public void setUp() {
    mealTypesActivityRule.getScenario().onActivity(activity -> mealTypesDecorView = activity.getWindow().getDecorView());
  }

  @Test
  public void clickGridItem_startsRecipeListActivity() {
    Intents.init();
    onData(anything())
            .inAdapterView(withId(R.id.meal_type_grid))
            .atPosition(0)
            .perform(click());
    Intents.intended(hasComponent(RecipeListActivity.class.getName()));
  }
}
