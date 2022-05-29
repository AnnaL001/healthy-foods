package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anna.healthyfoods.adapter.RecipeListAdapter;
import com.anna.healthyfoods.databinding.ActivityRecipeListBinding;

public class RecipeListActivity extends AppCompatActivity {
  private ActivityRecipeListBinding binding;
  private String[] recipeTitles = {"Chicken Vesuvio", "Green Bean Casserole Pie Recipe", "Baked Sweet Potatoes and Pecans", "Vegetable Lasagna", "Fresh tuna tortillas"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityRecipeListBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    initializeAdapter();
  }

  private void initializeAdapter(){
    RecipeListAdapter adapter = new RecipeListAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, recipeTitles);
    binding.recipeListView.setAdapter(adapter);
  }
}