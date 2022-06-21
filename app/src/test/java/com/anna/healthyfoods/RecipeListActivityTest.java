package com.anna.healthyfoods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;

import com.anna.healthyfoods.models.Settings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RunWith(RobolectricTestRunner.class)
public class RecipeListActivityTest {
  private RecipeListActivity activity;
  private RecyclerView recyclerView;

  @Before
  public void setUp()  {
    Intent intent = new Intent(ApplicationProvider.getApplicationContext(), HomeActivity.class);
    // Create lists of selected diets and preferences
    List<String> selectedDiets = new ArrayList<>(Collections.singletonList("high-protein"));
    List<String> selectedPreferences = new ArrayList<>(Collections.singletonList("gluten-free"));

    // Populate userSettings with name, selected diets and selected meal preferences
    Settings userSettings = new Settings("Anna", selectedDiets, selectedPreferences);

    intent.putExtra("user_settings", Parcels.wrap(userSettings));

    activity = Robolectric.buildActivity(RecipeListActivity.class, intent)
            .create()
            .start()
            .resume()
            .get();
    recyclerView = activity.findViewById(R.id.recipe_list);
  }

  @Test
  public void validateRecyclerView() {
    assertNotNull(recyclerView);
  }

  @Test
  public void validateDataReturned() {
    sleep();
    assertNotEquals(0, Objects.requireNonNull(recyclerView.getAdapter()).getItemCount());
  }

  @Test
  public void validateDataTypeReturned() {
    sleep();

    // Layout recyclerview with specified dimensions so that viewholders are inflated
    recyclerView.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
    );
    recyclerView.layout(0, 0, 1000, 1000);

    RecyclerView.ViewHolder recipeViewHolder = recyclerView.findViewHolderForAdapterPosition(0);
    Objects.requireNonNull(recipeViewHolder).itemView.performClick();

    Intent expectedIntent = new Intent(activity, RecipeDetailsActivity.class);
    expectedIntent.putExtra("recipe_id", "recipe_4bb99424e1bbc40d3cd1d891883d6745");
    Intent actualIntent = shadowOf(activity).getNextStartedActivity();

    assertEquals(expectedIntent.getStringExtra("recipe_id"), actualIntent.getStringExtra("recipe_id"));
  }

  private void sleep(){
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e){
      System.out.println("Got interrupted!");
    }
  }
}
