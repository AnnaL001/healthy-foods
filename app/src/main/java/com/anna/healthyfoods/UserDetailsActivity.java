package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anna.healthyfoods.databinding.ActivityUserDetailsBinding;
import com.anna.healthyfoods.utility.Validator;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDetailsActivity extends AppCompatActivity {
  public static final String TAG = UserDetailsActivity.class.getSimpleName();
  private ActivityUserDetailsBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    initializeButton();
  }

  private void initializeButton(){
    binding.btnNext.setOnClickListener(view -> {
      Intent intent = new Intent(getApplicationContext(), MealTypesActivity.class);

      // Capture user input
      String name = Objects.requireNonNull(binding.nameTextInputLayout.getEditText()).getText().toString();
      List<String> diets = getSelectedChips(binding.dietChipGroup);
      List<String> allergies = getSelectedChips(binding.allergyChipGroup);

      // Validate user input before populating intent extras
      if(Validator.validateRequiredUserDetailsFormInput(name, diets, allergies)){
        if(Validator.validateCorrectNameInput(name)){
          intent.putExtra("name", name);
          startActivity(intent);
          Log.i(TAG, "Navigating to MealTypesActivity...");
        } else {
          Toast.makeText(getApplicationContext(), getString(R.string.toast_correct_name_input), Toast.LENGTH_SHORT).show();
          Log.e(TAG, "Error: Name contains less than four characters");
        }
      } else {
        Toast.makeText(getApplicationContext(), getString(R.string.toast_required_input), Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Error: All input values are required");
      }
    });
  }

  // Retrieve selected chips and their corresponding labels
  private List<String> getSelectedChips(ChipGroup chipGroup){
    List<Integer> selectedChips = chipGroup.getCheckedChipIds();
    ArrayList<String> selectedOptions = new ArrayList<>();

    for(int selectedChip: selectedChips){
      Chip chip = chipGroup.findViewById(selectedChip);
      selectedOptions.add(chip.getText().toString());
    }

    return selectedOptions;
  }
}