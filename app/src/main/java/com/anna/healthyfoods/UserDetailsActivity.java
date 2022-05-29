package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.anna.healthyfoods.databinding.ActivityUserDetailsBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailsActivity extends AppCompatActivity {
  public static final String TAG = UserDetailsActivity.class.getSimpleName();
  private ActivityUserDetailsBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    binding.btnNext.setOnClickListener(view -> {
      Intent intent = new Intent(getApplicationContext(), MealTypesActivity.class);
      intent.putExtra("name", captureUserInput().get("name").toString());
      Log.d(TAG, "Name: " + captureUserInput().get("name").toString());
      startActivity(intent);
      Log.i(TAG, "Navigating to MealTypesActivity...");
    });
  }


  private Map<String, Object> captureUserInput(){
    Map<String, Object> userInput = new HashMap<>();
    userInput.put("name", binding.nameTextInputLayout.getEditText().getText().toString());
    userInput.put("diets", getSelectedChips(binding.dietChipGroup));
    userInput.put("allergies", getSelectedChips(binding.allergyChipGroup));
    return userInput;
  }

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