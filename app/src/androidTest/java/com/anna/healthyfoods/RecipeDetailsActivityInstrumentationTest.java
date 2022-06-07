package com.anna.healthyfoods;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.net.Uri;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeDetailsActivityInstrumentationTest {
  public static Intent initializeIntent(){
    Intent intent = new Intent(ApplicationProvider.getApplicationContext(), RecipeDetailsActivity.class);
    intent.putExtra("recipe_id", "recipe_2f0966f8aedcbd16f233bbb008f5df03");
    intent.putExtra("recipe_title", "Overnight Sous Vide Canadian Bacon or Breakfast Ham Recipe");
    return intent;
  }

  @Rule
  public ActivityScenarioRule<RecipeDetailsActivity> recipeDetailsActivityRule = new ActivityScenarioRule<>(initializeIntent());

  @Test
  public void clickRecipeSource_opensSourceWebsite() {
    // Sleep as data is fetched from the API
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e){
      System.out.println("Got interrupted!");
    }

    Intents.init();
    onView(withText("Serious Eats")).check(matches(isDisplayed())).perform(click());
    intended(hasAction(Intent.ACTION_VIEW));
  }
}
