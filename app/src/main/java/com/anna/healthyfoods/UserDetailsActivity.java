package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anna.healthyfoods.databinding.ActivityUserDetailsBinding;
import com.anna.healthyfoods.utility.UserInterfaceHelpers;
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

    Log.i(TAG, "UserDetailsActivity opened");
    initializeButton();
  }

  private void initializeButton(){
    binding.btnNext.setOnClickListener(view -> {
      Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

      // Capture user input
      String name = Objects.requireNonNull(binding.nameTextInputLayout.getEditText()).getText().toString();
      List<String> diets = UserInterfaceHelpers.getSelectedChips(binding.dietChipGroup);
      List<String> allergies = UserInterfaceHelpers.getSelectedChips(binding.allergyChipGroup);

      // Validate user input
      validateFormInputs(intent, name, diets, allergies);
    });
  }

  // Validate user input before populating intent extras
  private void validateFormInputs(Intent intent, String name, List<String> diets, List<String> allergies){
    if(Validator.validateRequiredUserDetailsFormInput(name, diets, allergies)){
      if(Validator.validateCorrectNameInput(name)){
        intent.putExtra("name", name);

        // Clear form after capturing data
        clearFormInputs();

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
  }

  private void clearFormInputs(){
    UserInterfaceHelpers.clearFormInput(binding.nameTextInputLayout);
    UserInterfaceHelpers.clearFormInput(binding.dietChipGroup);
    UserInterfaceHelpers.clearFormInput(binding.allergyChipGroup);
  }

}