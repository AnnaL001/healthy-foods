package com.anna.healthyfoods.ui;

import static com.anna.healthyfoods.utility.UserInterfaceHelpers.hideProgressDialog;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showFailureFeedback;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showNoContentFound;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showRecipes;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showUnsuccessfulFeedback;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.RecipeDetailsActivity;
import com.anna.healthyfoods.adapter.RecipeListAdapter;
import com.anna.healthyfoods.client.EdamamClient;
import com.anna.healthyfoods.databinding.FragmentRecipeListBinding;
import com.anna.healthyfoods.interfaces.EdamamApi;
import com.anna.healthyfoods.interfaces.ItemOnClickListener;
import com.anna.healthyfoods.models.RecipeSearchResponse;
import com.anna.healthyfoods.models.Settings;
import com.anna.healthyfoods.utility.Constants;

import org.parceler.Parcels;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListFragment extends Fragment implements ItemOnClickListener {
  public static final String TAG = RecipeListFragment.class.getSimpleName();
  private FragmentRecipeListBinding binding;
  private String mealType;
  private Settings userSettings;
  private RecipeListAdapter adapter;

  public RecipeListFragment() {
    // Required empty public constructor
  }

  public static RecipeListFragment newInstance(String mealType, Settings userSettings) {
    RecipeListFragment fragment = new RecipeListFragment();
    Bundle args = new Bundle();
    args.putString(Constants.EXTRA_MEAL_TYPE, mealType);
    args.putParcelable(Constants.EXTRA_USER_SETTINGS, Parcels.wrap(userSettings));
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mealType = getArguments().getString(Constants.EXTRA_MEAL_TYPE);
      userSettings = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_USER_SETTINGS));
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the fragment layout
    binding = FragmentRecipeListBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Set action bar title to passed meal type
    ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
    Objects.requireNonNull(actionBar).setTitle(mealType);

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
          adapter = new RecipeListAdapter(getContext(), response.body().getHits(), RecipeListFragment.this);

          setLayoutManager();

          binding.recipeList.setAdapter(adapter);

          if(adapter.getItemCount() > 0){
            showRecipes(binding.recipeList);
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
      binding.recipeList.setLayoutManager(new GridLayoutManager(getContext(), 2));
    } else {
      binding.recipeList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
  }


  @Override
  public void onClick(String id, boolean isSaved) {
    Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
    intent.putExtra(Constants.EXTRA_RECIPE_ID, id);
    intent.putExtra(Constants.EXTRA_SAVED, isSaved);
    Log.d(TAG, "Recipe ID: " + id);
    startActivity(intent);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}