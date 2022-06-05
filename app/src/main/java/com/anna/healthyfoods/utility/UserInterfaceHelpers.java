package com.anna.healthyfoods.utility;

import android.text.TextUtils;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class UserInterfaceHelpers {
  // Retrieve selected chips and their corresponding labels
  public static List<String> getSelectedChips(ChipGroup chipGroup){
    List<Integer> selectedChips = chipGroup.getCheckedChipIds();
    ArrayList<String> selectedOptions = new ArrayList<>();

    for(int selectedChip: selectedChips){
      Chip chip = chipGroup.findViewById(selectedChip);
      String transformed = transformString(chip.getText().toString());
      selectedOptions.add(transformed);
    }

    return selectedOptions;
  }

  private static String transformString(String selection){
    // Transform to an array
    String[] stringArray = selection.toLowerCase().split(" ");
    // Join array to string with hyphen
    return TextUtils.join("-", stringArray);
  }

  public static void clearFormInput(TextInputLayout textInputLayout){
    Objects.requireNonNull(textInputLayout.getEditText()).setText("");
  }

  public static void clearFormInput(ChipGroup chipGroup){
    chipGroup.clearCheck();
  }
}
