package com.anna.healthyfoods.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.RecipeDetailsActivity;
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

  public SearchFragment(){
  }

  public static SearchFragment newInstance(Settings userSettings){
    SearchFragment fragment = new SearchFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable("userSettings", Parcels.wrap(userSettings));
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    assert getArguments() != null;
    userSettings = Parcels.unwrap(getArguments().getParcelable("userSettings"));
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

    EdamamApi client = EdamamClient.getClient();

    // Load recipes based on search
    binding.btnSearch.setOnClickListener(buttonView -> {
      Call<RecipeSearchResponse> call = client.getRecipesByKeyword("public", binding.searchView.getQuery().toString(), Constants.EDAMAM_API_ID, Constants.EDAMAM_API_KEY, userSettings.getDiets().toArray(diets), userSettings.getPreferences().toArray(preferences));
      loadRecipes(call);
    });
  }

  private void loadRecipes(Call<RecipeSearchResponse> call){
    call.enqueue(new Callback<RecipeSearchResponse>() {
      @Override
      public void onResponse(@NonNull Call<RecipeSearchResponse> call, @NonNull Response<RecipeSearchResponse> response) {
        if(response.isSuccessful()){
          assert response.body() != null;
          adapter = new RecipeListAdapter(getContext(), response.body().getHits(), SearchFragment.this);
          binding.recipeResultList.setLayoutManager(new LinearLayoutManager(getContext()));
          binding.recipeResultList.setAdapter(adapter);

          if(adapter.getItemCount() > 0){
            UserInterfaceHelpers.showRecipes(binding.recipeResultList);
          } else {
            UserInterfaceHelpers.showNoContentFound(binding.errorText, requireContext(), getString(R.string.no_recipes_found));
          }
        } else {
          UserInterfaceHelpers.showUnsuccessfulFeedback(binding.errorText, requireContext());
        }
      }

      @Override
      public void onFailure(@NonNull Call<RecipeSearchResponse> call, @NonNull Throwable t) {
        UserInterfaceHelpers.showFailureFeedback(binding.errorText, requireContext());
        Log.e(TAG, "Error: ", t);
      }
    });
  }

  @Override
  public void onClick(String title, String id) {
    Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
    intent.putExtra("recipe_title", title);
    intent.putExtra("recipe_id", id);
    Log.d(TAG, "Recipe ID: " + id);
    startActivity(intent);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
