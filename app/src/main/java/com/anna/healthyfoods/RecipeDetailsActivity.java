package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anna.healthyfoods.databinding.ActivityRecipeDetailsBinding;

public class RecipeDetailsActivity extends AppCompatActivity {
  private ActivityRecipeDetailsBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
  }
}