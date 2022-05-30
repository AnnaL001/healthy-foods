package com.anna.healthyfoods;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.anna.healthyfoods.utility.UserInterfaceHelpers;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RunWith(RobolectricTestRunner.class)
public class UserDetailsActivityTest {
  private UserDetailsActivity activity;
  private String name;
  private TextInputLayout nameTextInputLayout;
  private ChipGroup dietChipGroup;
  private ChipGroup allergyChipGroup;

  @Before
  public void setUp()  {
    name = "Anna";
    activity = Robolectric.buildActivity(UserDetailsActivity.class)
            .create()
            .start()
            .resume()
            .get();
    nameTextInputLayout = activity.findViewById(R.id.name_text_input_layout);
    dietChipGroup = activity.findViewById(R.id.diet_chip_group);
    allergyChipGroup = activity.findViewById(R.id.allergy_chip_group);
  }

  @Test
  public void validateTextViewContent() {
    TextView formTitle = activity.findViewById(R.id.user_details_form_subtitle);
    assertEquals(activity.getString(R.string.user_details_form_subtitle), formTitle.getText().toString());
  }

  @Test
  public void validateTextInputLayoutContent() {
    assertEquals(activity.getString(R.string.name), Objects.requireNonNull(nameTextInputLayout.getEditText()).getHint());
  }

  @Test
  public void validateChipGroupContent() {
    assertNotNull(dietChipGroup);
    assertNotNull(allergyChipGroup);
  }

  @Test
  public void validateButtonContent() {
    Button nextButton = activity.findViewById(R.id.btn_next);
    assertEquals(activity.getString(R.string.next), nextButton.getText().toString());
  }

  @Test
  public void getSelectedChips_returnsSelectedChipsLabel() {
    // Select preferred diet
    dietChipGroup.check(R.id.keto_diet);
    // Select allergies
    allergyChipGroup.check(R.id.glutten);
    allergyChipGroup.check(R.id.dairy);

    // Expected list of diets and allergies
    List<String> expectedSelectedDiets = new ArrayList<>(Collections.singletonList(activity.getString(R.string.keto_diet)));
    List<String> expectedSelectedAllergies = new ArrayList<>(Arrays.asList(activity.getString(R.string.glutten_free), activity.getString(R.string.dairy_free)));

    assertEquals(expectedSelectedDiets, UserInterfaceHelpers.getSelectedChips(dietChipGroup));
    assertEquals(expectedSelectedAllergies, UserInterfaceHelpers.getSelectedChips(allergyChipGroup));
  }

  @Test
  public void initializeButton_startsNextActivityWithDataUponAClick() {
    // Fill in the User details form
    Objects.requireNonNull(nameTextInputLayout.getEditText()).setText(name);
    dietChipGroup.check(R.id.keto_diet);
    allergyChipGroup.check(R.id.glutten);

    // Click next button
    activity.findViewById(R.id.btn_next).performClick();

    //Expected Intent with extras
    Intent expectedIntent = new Intent(activity, MealTypesActivity.class);
    expectedIntent.putExtra("name", nameTextInputLayout.getEditText().getText().toString());

    // Actual intent
    Intent actualIntent = shadowOf(activity).getNextStartedActivity();
    assertEquals(expectedIntent.getStringExtra("name"), actualIntent.getStringExtra("name"));
  }
}