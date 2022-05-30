package com.anna.healthyfoods;

import static org.junit.Assert.*;

import android.widget.GridView;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)
public class MealTypesActivityTest {
  private MealTypesActivity activity;

  @Before
  public void setUp() {
    activity = Robolectric.buildActivity(MealTypesActivity.class)
            .create()
            .start()
            .resume()
            .get();
  }

  @Test
  public void validateTextViewContent() {
    TextView welcomeText = activity.findViewById(R.id.welcome_text);
    TextView mealInquiry = activity.findViewById(R.id.meal_inquiry);

    assertEquals(activity.getString(R.string.meal_inquiry), mealInquiry.getText().toString());
    assertNotNull(welcomeText);
  }

  @Test
  public void validateGridContent() {
    GridView gridView = activity.findViewById(R.id.meal_type_grid);
    assertNotNull(gridView);
  }
}