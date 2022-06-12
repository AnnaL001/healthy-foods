package com.anna.healthyfoods;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anna.healthyfoods.adapter.IngredientListAdapter;
import com.anna.healthyfoods.client.EdamamClient;
import com.anna.healthyfoods.databinding.ActivityRecipeDetailsBinding;
import com.anna.healthyfoods.interfaces.EdamamApi;
import com.anna.healthyfoods.models.Hit;
import com.anna.healthyfoods.models.Recipe;
import com.anna.healthyfoods.utility.Constants;
import com.anna.healthyfoods.utility.UserInterfaceHelpers;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends AppCompatActivity {
  public static final String TAG = RecipeDetailsActivity.class.getSimpleName();
  private ActivityRecipeDetailsBinding binding;
  private String recipeId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    recipeId =  getIntent().getStringExtra("recipe_id");

    EdamamApi client = EdamamClient.getClient();
    Call<Hit> call = client.getRecipeById(recipeId,
            "public", Constants.EDAMAM_API_ID, Constants.EDAMAM_API_KEY);

    loadRecipe(call);
  }

  private void loadRecipe(Call<Hit> call){
    call.enqueue(new Callback<Hit>() {
      @Override
      public void onResponse(@NonNull Call<Hit> call, @NonNull Response<Hit> response) {
        UserInterfaceHelpers.hideProgressBar(binding.progressBar);

        if(response.isSuccessful()){
          assert response.body() != null;
          setRecipeDetails(response.body().getRecipe());
          UserInterfaceHelpers.showRecipeDetails(binding.recipeImage, binding.detailsBottomSheetGroup);
        } else {
          UserInterfaceHelpers.showUnsuccessfulFeedback(binding.errorFeedback, getApplicationContext());
        }
      }

      @Override
      public void onFailure(@NonNull Call<Hit> call, @NonNull Throwable t) {
        UserInterfaceHelpers.hideProgressBar(binding.progressBar);
        UserInterfaceHelpers.showFailureFeedback(binding.errorFeedback, getApplicationContext());
        Log.e(TAG, "Error while fetching recipe with ID: " + recipeId, t);
      }
    });
  }

  private void setRecipeDetails(Recipe recipe){
    Glide.with(getApplicationContext()).asBitmap().load(recipe.getImages().getRegular().getUrl()).placeholder(R.drawable.brunch_dining).into(binding.recipeImage);
    binding.recipeLabel.setText(recipe.getLabel());
    binding.recipeSource.setText(recipe.getSource());

    // Add listener to recipe source to open website
    openWebsite(recipe.getUrl());

    // Set calories and serving quantity
    binding.caloriesQuantity.setText(String.format(Locale.ENGLISH, "%.2f", recipe.getCalories()));
    binding.yieldQuantity.setText(String.format(Locale.ENGLISH, "%d", recipe.getYield()));

    // Set ingredient list
    binding.ingredientList.setLayoutManager(new LinearLayoutManager(this));
    binding.ingredientList.setAdapter(new IngredientListAdapter(this, recipe.getIngredients()));

    // Set recipe type labels: meal type, dish type, cuisine type
    for(String mealType: recipe.getMealType()){
      Chip chip = new Chip(this);
      chip.setText(mealType);
      binding.recipeTypeLabels.addView(chip);
    }

    for (String dishType: recipe.getDishType()){
      Chip chip = new Chip(this);
      chip.setText(dishType);
      binding.recipeTypeLabels.addView(chip);
    }

    for(String cuisineType: recipe.getCuisineType()){
      Chip chip = new Chip(this);
      chip.setText(cuisineType);
      binding.recipeTypeLabels.addView(chip);
    }

    // Set recipe diet and health labels
    for(String dietLabel: recipe.getDietLabels()){
      Chip chip = new Chip(this);
      chip.setText(dietLabel);
      binding.recipeDietHealthLabels.addView(chip);
    }

    for (String healthLabel: recipe.getHealthLabels()){
      Chip chip = new Chip(this);
      chip.setText(healthLabel);
      binding.recipeDietHealthLabels.addView(chip);
    }

    // Set recipe nutrients
    binding.cholestrolQuantity.setText(String.format(Locale.ENGLISH, "%.2f %s", recipe.getTotalNutrients().getChole().getQuantity(), recipe.getTotalNutrients().getChole().getUnit()));
    binding.sodiumQuantity.setText(String.format(Locale.ENGLISH, "%.2f %s", recipe.getTotalNutrients().getNa().getQuantity(), recipe.getTotalNutrients().getNa().getUnit()));
    binding.calciumQuantity.setText(String.format(Locale.ENGLISH, "%.2f %s", recipe.getTotalNutrients().getCa().getQuantity(), recipe.getTotalNutrients().getCa().getUnit()));
    binding.magnesiumQuantity.setText(String.format(Locale.ENGLISH, "%.2f %s", recipe.getTotalNutrients().getMg().getQuantity(), recipe.getTotalNutrients().getMg().getUnit()));
    binding.potassiumQuantity.setText(String.format(Locale.ENGLISH, "%.2f %s", recipe.getTotalNutrients().getK().getQuantity(), recipe.getTotalNutrients().getK().getUnit()));
    binding.ironQuantity.setText(String.format(Locale.ENGLISH, "%.2f %s", recipe.getTotalNutrients().getFe().getQuantity(), recipe.getTotalNutrients().getFe().getUnit()));
  }

  private void openWebsite(String websiteUrl){
    binding.recipeSource.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
      startActivity(intent);
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.recipe_details_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.action_starr) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}