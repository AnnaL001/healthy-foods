package com.anna.healthyfoods;

import static org.junit.Assert.assertNotNull;

import android.content.Intent;

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

@RunWith(RobolectricTestRunner.class)
public class RecipeListActivityTest {
  private RecipeListActivity activity;

  @Before
  public void setUp()  {
    Intent intent = new Intent(ApplicationProvider.getApplicationContext(), HomeActivity.class);
    // Create lists of selected diets and preferences
    List<String> selectedDiets = new ArrayList<>(Collections.singletonList("high-protein"));
    List<String> selectedPreferences = new ArrayList<>(Collections.singletonList("gluten-free"));

    // Populate userSettings with name, selected diets and selected meal preferences
    Settings userSettings = new Settings("Anna", selectedDiets, selectedPreferences);

    intent.putExtra("userSettings", Parcels.wrap(userSettings));

    activity = Robolectric.buildActivity(RecipeListActivity.class, intent)
            .create()
            .start()
            .resume()
            .get();
  }

  @Test
  public void validateRecyclerView() {
    RecyclerView recyclerView = activity.findViewById(R.id.recipe_list);
    assertNotNull(recyclerView);
  }
}
