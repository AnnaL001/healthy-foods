package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.anna.healthyfoods.adapter.MealTypeAdapter;
import com.anna.healthyfoods.databinding.ActivityMealTypesBinding;
import com.anna.healthyfoods.interfaces.ItemOnClickListener;

public class MealTypesActivity extends AppCompatActivity implements ItemOnClickListener {
  public static final String TAG = MealTypesActivity.class.getSimpleName();
  private ActivityMealTypesBinding binding;

  // Set up list data
  private final int[] mealTypeImages={R.drawable.breakfast, R.drawable.lunch, R.drawable.brunch, R.drawable.snack, R.drawable.teatime};
  private final int[] mealTypeTitles={R.string.breakfast, R.string.lunch, R.string.brunch, R.string.snack, R.string.teatime};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMealTypesBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    Log.i(TAG, "MealTypesActivity opened");

    binding.welcomeText.setText(getString(R.string.welcome, getIntent().getStringExtra("name")));

    initializeAdapter();
  }

  private void initializeAdapter(){
    binding.mealTypeGrid.setAdapter(new MealTypeAdapter(getApplicationContext(), mealTypeImages, mealTypeTitles, this));
  }

  @Override
  public void onClick(int title) {
    Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
    // Add selected meal type to intent
    intent.putExtra("meal_type", title);
    Log.d(TAG, String.format("Meal Type Clicked: %s", getString(title)));
    startActivity(intent);
  }
}