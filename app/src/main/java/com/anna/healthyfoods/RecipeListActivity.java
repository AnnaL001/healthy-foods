package com.anna.healthyfoods;

import static com.anna.healthyfoods.utility.UserInterfaceHelpers.hideProgressDialog;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showFailureFeedback;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showNoContentFound;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showRecipes;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showUnsuccessfulFeedback;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anna.healthyfoods.adapter.RecipeListAdapter;
import com.anna.healthyfoods.client.EdamamClient;
import com.anna.healthyfoods.databinding.ActivityRecipeListBinding;
import com.anna.healthyfoods.interfaces.EdamamApi;
import com.anna.healthyfoods.interfaces.ItemOnClickListener;
import com.anna.healthyfoods.models.RecipeSearchResponse;
import com.anna.healthyfoods.models.Settings;
import com.anna.healthyfoods.utility.Constants;
import com.anna.healthyfoods.utility.UserInterfaceHelpers;

import org.parceler.Parcels;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity implements ItemOnClickListener {
  public static final String TAG = RecipeListActivity.class.getSimpleName();
  private ActivityRecipeListBinding binding;
  private RecipeListAdapter adapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityRecipeListBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    String mealType = getIntent().getStringExtra("meal_type");
    Settings userSettings = Parcels.unwrap(getIntent().getParcelableExtra("userSettings"));

    // Set action bar title to passed meal type
    Objects.requireNonNull(this.getSupportActionBar()).setTitle(mealType);

    // Create arrays for diet and health preferences for recipes
    String[] diets = new String[userSettings.getDiets().size()];
    String[] preferences = new String[userSettings.getPreferences().size()];

    EdamamApi client = EdamamClient.getClient();
    Call<RecipeSearchResponse> call = client.getRecipesByMealType("public", "", Constants.EDAMAM_API_ID, Constants.EDAMAM_API_KEY, mealType, userSettings.getDiets().toArray(diets), userSettings.getPreferences().toArray(preferences));

    loadRecipes(call);
  }

  private void loadRecipes(Call<RecipeSearchResponse> call){
    call.enqueue(new Callback<RecipeSearchResponse>() {
      @Override
      public void onResponse(@NonNull Call<RecipeSearchResponse> call, @NonNull Response<RecipeSearchResponse> response) {
        hideProgressDialog(binding.progressBar, binding.progressMessage);

        if(response.isSuccessful()){
          assert response.body() != null;
          adapter = new RecipeListAdapter(RecipeListActivity.this, response.body().getHits(), RecipeListActivity.this);

          setLayoutManager();

          binding.recipeList.setAdapter(adapter);

          if(adapter.getItemCount() > 0){
            showRecipes(binding.recipeList);
          } else {
            showNoContentFound(binding.errorText, getString(R.string.no_recipes_found));
          }
        } else {
          showUnsuccessfulFeedback(binding.errorText, getApplicationContext());
        }
      }

      @Override
      public void onFailure(@NonNull Call<RecipeSearchResponse> call, @NonNull Throwable t) {
        hideProgressDialog(binding.progressBar, binding.progressMessage);
        showFailureFeedback(binding.errorText, getApplicationContext());
        Log.e(TAG, "Error: ", t);
      }
    });
  }

  private void setLayoutManager(){
    // Set layout manager based on orientation
    if(binding.getRoot().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
      binding.recipeList.setLayoutManager(new GridLayoutManager(RecipeListActivity.this, 2));
    } else {
      binding.recipeList.setLayoutManager(new LinearLayoutManager(RecipeListActivity.this));
    }
  }


  @Override
  public void onClick(String id) {
    Intent intent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
    intent.putExtra(Constants.EXTRA_RECIPE_ID, id);
    Log.d(TAG, "Recipe ID: " + id);
    startActivity(intent);
  }
}