package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.anna.healthyfoods.adapter.RecipeListAdapter;
import com.anna.healthyfoods.databinding.ActivityRecipeListBinding;

import java.util.Objects;

public class RecipeListActivity extends AppCompatActivity {
  public static final String TAG = RecipeListActivity.class.getSimpleName();
  private ActivityRecipeListBinding binding;
  private final String[] recipeTitles = {"Chicken Vesuvio", "Green Bean Casserole Pie Recipe", "Baked Sweet Potatoes and Pecans", "Vegetable Lasagna", "Fresh tuna tortillas"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityRecipeListBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    String title = getString(getIntent().getIntExtra("meal_type", R.string.breakfast));
    Log.d("RecipeListActivity", String.format("Displaying %s recipes", title));

    // Set Action Bar title to meal type passed via intent
    Objects.requireNonNull(this.getSupportActionBar()).setTitle(title);

    initializeAdapter();
    initializeOnClickListener();
  }

  private void initializeAdapter(){
    // Add code to initialize adapter
  }

  // Initialize listener for clicks on list items
  private void initializeOnClickListener(){
    // Add code for click listener
  }
}