package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.anna.healthyfoods.databinding.ActivityMealTypesBinding;

public class MealTypesActivity extends AppCompatActivity {
  public static final String TAG = MealTypesActivity.class.getSimpleName();
  private ActivityMealTypesBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMealTypesBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    Log.i(TAG, "MealTypesActivity opened");
    binding.welcomeText.setText(getString(R.string.welcome, getIntent().getStringExtra("name")));
  }
}