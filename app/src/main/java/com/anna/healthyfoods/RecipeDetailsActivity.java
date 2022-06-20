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
  private boolean recipeStatus;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // Retrieve intent extras
    recipeId = getIntent().getStringExtra(Constants.EXTRA_RECIPE_ID);
    recipeStatus = getIntent().getBooleanExtra(Constants.EXTRA_SAVED, false);

    inflateFragment();
  }

  // Inflate RecipeDetailsFragment with extras
  private void inflateFragment(){
    getSupportFragmentManager().beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_container, RecipeDetailsFragment.newInstance(recipeId, recipeStatus))
            .commit();
  }

}