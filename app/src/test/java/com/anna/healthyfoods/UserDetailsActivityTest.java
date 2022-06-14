package com.anna.healthyfoods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
  private TextInputLayout nameTextInputLayout;
  private ChipGroup dietChipGroup;
  private ChipGroup allergyChipGroup;

  @Before
  public void setUp()  {
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
    dietChipGroup.check(R.id.high_protein_diet);
    // Select allergies
    allergyChipGroup.check(R.id.gluten_free);
    allergyChipGroup.check(R.id.vegan);

    // Expected list of diets and allergies
    List<String> expectedSelectedDiets = new ArrayList<>(Collections.singletonList("high-protein"));
    List<String> expectedSelectedAllergies = new ArrayList<>(Arrays.asList("gluten-free", "vegan"));

    assertEquals(expectedSelectedDiets, UserInterfaceHelpers.getSelectedChips(dietChipGroup));
    assertEquals(expectedSelectedAllergies, UserInterfaceHelpers.getSelectedChips(allergyChipGroup));
  }
}