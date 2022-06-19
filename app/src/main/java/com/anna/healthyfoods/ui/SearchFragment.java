package com.anna.healthyfoods.ui;

import static com.anna.healthyfoods.utility.UserInterfaceHelpers.hideProgressDialog;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showFailureFeedback;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showNoContentFound;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showProgressDialog;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showRecipes;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showUnsuccessfulFeedback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.RecipeDetailsActivity;
import com.anna.healthyfoods.RecipeListActivity;
import com.anna.healthyfoods.adapter.RecipeListAdapter;
import com.anna.healthyfoods.client.EdamamClient;
import com.anna.healthyfoods.databinding.FragmentSearchBinding;
import com.anna.healthyfoods.interfaces.EdamamApi;
import com.anna.healthyfoods.interfaces.ItemOnClickListener;
import com.anna.healthyfoods.models.RecipeSearchResponse;
import com.anna.healthyfoods.models.Settings;
import com.anna.healthyfoods.utility.Constants;
import com.anna.healthyfoods.utility.UserInterfaceHelpers;
import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements ItemOnClickListener {
  public static final String TAG = SearchFragment.class.getSimpleName();
  private FragmentSearchBinding binding;
  private Settings userSettings;
  private RecipeListAdapter adapter;
  private EdamamApi client;
  private SharedPreferences sharedPreferences;

  public SearchFragment(){
  }

  public static SearchFragment newInstance(Settings userSettings){
    SearchFragment fragment = new SearchFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(Constants.EXTRA_USER_SETTINGS, Parcels.wrap(userSettings));
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    assert getArguments() != null;
    userSettings = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_USER_SETTINGS));
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentSearchBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Create arrays for diet and health preferences for recipes
    String[] diets = new String[userSettings.getDiets().size()];
    String[] preferences = new String[userSettings.getPreferences().size()];

    client = EdamamClient.getClient();

    // Set up shared preferences
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    String recentSearch = sharedPreferences.getString(Constants.PREFERENCES_RECIPE_SEARCH_KEY, null);
    Log.d(TAG, "Recently searched recipe: " + recentSearch);

    // Initial display of recipes based on previous search
    loadRecipes(recentSearch, diets, preferences);

    // Load recipes based on search
    setUpSearchView(diets, preferences);
  }

  private void setUpSearchView(String[] diets, String[] preferences){
    binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String recipe) {
        saveToSharedPreferences(recipe);
        loadRecipes(recipe, diets, preferences);
        return false;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        return false;
      }
    });
  }

  private void loadRecipes(String recipe, String[] diets, String[] preferences){
    Call<RecipeSearchResponse> call = client.getRecipesByKeyword("public", recipe, Constants.EDAMAM_API_ID, Constants.EDAMAM_API_KEY, userSettings.getDiets().toArray(diets), userSettings.getPreferences().toArray(preferences));
    showProgressDialog(binding.progressBar, binding.progressMessage);

    call.enqueue(new Callback<RecipeSearchResponse>() {
      @Override
      public void onResponse(@NonNull Call<RecipeSearchResponse> call, @NonNull Response<RecipeSearchResponse> response) {
        hideProgressDialog(binding.progressBar, binding.progressMessage);

        if(response.isSuccessful()){
          assert response.body() != null;
          adapter = new RecipeListAdapter(getContext(), response.body().getHits(), SearchFragment.this);
          setLayoutManager();
          binding.recipeResultList.setAdapter(adapter);

          if(adapter.getItemCount() > 0){
            showRecipes(binding.recipeResultList);
          } else {
            showNoContentFound(binding.errorText, getString(R.string.no_recipes_found));
          }
        } else {
          showUnsuccessfulFeedback(binding.errorText, requireContext());
        }
      }

      @Override
      public void onFailure(@NonNull Call<RecipeSearchResponse> call, @NonNull Throwable t) {
        hideProgressDialog(binding.progressBar, binding.progressMessage);
        showFailureFeedback(binding.errorText, requireContext());
        Log.e(TAG, "Error: ", t);
      }
    });
  }

  private void setLayoutManager(){
    // Set layout manager based on orientation
    if(binding.getRoot().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
      binding.recipeResultList.setLayoutManager(new GridLayoutManager(getContext(), 2));
    } else {
      binding.recipeResultList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
  }

  private void saveToSharedPreferences(String recipeSearch) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(Constants.PREFERENCES_RECIPE_SEARCH_KEY, recipeSearch).apply();
  }

  @Override
  public void onClick(String id) {
    Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
    intent.putExtra(Constants.EXTRA_RECIPE_ID, id);
    intent.putExtra(Constants.EXTRA_SAVED, "Not saved");
    Log.d(TAG, "Recipe ID: " + id);
    startActivity(intent);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
