package com.anna.healthyfoods;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anna.healthyfoods.databinding.ActivityUserDetailsBinding;
import com.anna.healthyfoods.models.Settings;
import com.anna.healthyfoods.utility.Constants;
import com.anna.healthyfoods.utility.UserInterfaceHelpers;
import com.anna.healthyfoods.utility.Validator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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
      // Capture user input
      String name = Objects.requireNonNull(binding.nameTextInputLayout.getEditText()).getText().toString();
      List<String> diets = UserInterfaceHelpers.getSelectedChips(binding.dietChipGroup);
      List<String> allergies = UserInterfaceHelpers.getSelectedChips(binding.allergyChipGroup);

      // Validate user input
      validateFormInputs(name, diets, allergies);
    });
  }


  // Validate user input before populating intent extras
  private void validateFormInputs(String name, List<String> diets, List<String> healthPreferences){
    if(Validator.validateRequiredUserDetailsFormInput(name, diets, healthPreferences)){
      if(Validator.validateNameInput(name)){
        Settings userSettings = new Settings(name, diets, healthPreferences);
        // Save settings to Firebase
        saveUserSettings(userSettings);

        // Clear form after capturing data
        clearFormInputs();

        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        Log.i(TAG, "Navigating to MealTypesActivity...");
      } else {
        binding.nameTextInputLayout.setError(getString(R.string.toast_correct_name_input));
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

  private void saveUserSettings(Settings userSettings){
    String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CHILD_SETTINGS_LOCATION).child(userId);
    databaseReference.setValue(userSettings);
    Toast.makeText(getApplicationContext(), R.string.saved, Toast.LENGTH_SHORT).show();
  }
}