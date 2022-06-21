package com.anna.healthyfoods;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListActivityInstrumentationTest {
  public static Intent initializeIntent(){
    Intent intent = new Intent(ApplicationProvider.getApplicationContext(), RecipeListActivity.class);

    // Create lists of selected diets and preferences
    List<String> selectedDiets = new ArrayList<>(Collections.singletonList("high-protein"));
    List<String> selectedPreferences = new ArrayList<>(Collections.singletonList("gluten-free"));

    // Populate userSettings with name, selected diets and selected meal preferences
    Settings userSettings = new Settings("Anna", selectedDiets, selectedPreferences);

    intent.putExtra("user_settings", Parcels.wrap(userSettings));
    return intent;
  }


  @Rule
  public ActivityScenarioRule<RecipeListActivity> recipeListActivityRule = new ActivityScenarioRule<>(initializeIntent());
  private View recipeListDecorView;

  @Before
  public void setUp()  {
    recipeListActivityRule.getScenario().onActivity(activity -> recipeListDecorView = activity.getWindow().getDecorView());
  }

  @Test
  public void clickRecipe_opensRecipeDetailsActivityScreen() {
    // Sleep as data is fetched from the API
    sleep();

    Intents.init();
    // Click first item on the list
    onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    // Confirm correct activity is launched
    Intents.intended(hasComponent(RecipeDetailsActivity.class.getName()));
  }

  @Test
  public void clickRecipe_displaysClickedRecipeDetails() {
    String recipeTitle = "Frothy Iced Matcha Green Tea Recipe";

    // Sleep as data is fetched from Edamam API
    sleep();

    Intents.init();
    // Click first item on the list
    onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    // Confirm display of correct recipe details
    onView(withId(R.id.recipe_label)).check(matches(withText(recipeTitle)));
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