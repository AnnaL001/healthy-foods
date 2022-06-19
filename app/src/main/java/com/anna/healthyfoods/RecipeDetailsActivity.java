package com.anna.healthyfoods;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anna.healthyfoods.databinding.ActivityRecipeDetailsBinding;
import com.anna.healthyfoods.ui.RecipeDetailsFragment;
import com.anna.healthyfoods.utility.Constants;

public class RecipeDetailsActivity extends AppCompatActivity {
  public static final String TAG = RecipeDetailsActivity.class.getSimpleName();
  private ActivityRecipeDetailsBinding binding;
  private String recipeId;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    recipeId = getIntent().getStringExtra(Constants.EXTRA_RECIPE_ID);
    inflateFragment();
  }

  private void inflateFragment(){
    getSupportFragmentManager().beginTransaction()
            .add(R.id.fragment_container, RecipeDetailsFragment.newInstance(recipeId))
            .commit();
  }

}