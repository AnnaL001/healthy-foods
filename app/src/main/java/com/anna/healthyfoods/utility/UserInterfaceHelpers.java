package com.anna.healthyfoods.utility;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserInterfaceHelpers {
  // Retrieve selected chips and their corresponding labels
  public static List<String> getSelectedChips(ChipGroup chipGroup){
    List<Integer> selectedChips = chipGroup.getCheckedChipIds();
    ArrayList<String> selectedOptions = new ArrayList<>();

    for(int selectedChip: selectedChips){
      Chip chip = chipGroup.findViewById(selectedChip);
      selectedOptions.add(chip.getText().toString());
    }

    return selectedOptions;
  }

  public static void clearFormInput(TextInputLayout textInputLayout){
    Objects.requireNonNull(textInputLayout.getEditText()).setText("");
  }

  public static void clearFormInput(ChipGroup chipGroup){
    chipGroup.clearCheck();
  }
}
