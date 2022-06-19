package com.anna.healthyfoods.ui;

import static com.anna.healthyfoods.utility.UserInterfaceHelpers.hideProgressDialog;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showFailureFeedback;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showRecipeDetails;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showUnsuccessfulFeedback;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.adapter.IngredientListAdapter;
import com.anna.healthyfoods.client.EdamamClient;
import com.anna.healthyfoods.databinding.FragmentRecipeDetailsBinding;
import com.anna.healthyfoods.interfaces.EdamamApi;
import com.anna.healthyfoods.models.Hit;
import com.anna.healthyfoods.models.Recipe;
import com.anna.healthyfoods.utility.Constants;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsFragment extends Fragment {
  public static final String TAG = RecipeDetailsFragment.class.getSimpleName();
  private FragmentRecipeDetailsBinding binding;
  private String recipeId;
  private static final String ARG_RECIPE_ID = "recipe_id";

  public RecipeDetailsFragment() {
    // Required empty public constructor
  }

  public static RecipeDetailsFragment newInstance(String recipeId) {
    RecipeDetailsFragment fragment = new RecipeDetailsFragment();
    Bundle args = new Bundle();
    args.putString(ARG_RECIPE_ID, recipeId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      recipeId = getArguments().getString(ARG_RECIPE_ID);
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    EdamamApi client = EdamamClient.getClient();
    Call<Hit> call = client.getRecipeById(recipeId,
            "public", Constants.EDAMAM_API_ID, Constants.EDAMAM_API_KEY);
    loadRecipe(call);
  }

  private void setUpSaveButton(Recipe recipe) {
    binding.btnSave.setOnClickListener(view -> saveRecipe(recipe));
  }

  // Load recipe details
  private void loadRecipe(Call<Hit> call){
    call.enqueue(new Callback<Hit>() {
      @Override
      public void onResponse(@NonNull Call<Hit> call, @NonNull Response<Hit> response) {
        hideProgressDialog(binding.progressBar, binding.progressMessage);

        if(response.isSuccessful()){
          assert response.body() != null;
          setRecipeDetails(response.body().getRecipe());
          showRecipeDetails(binding.recipeImage, binding.detailsBottomSheetGroup);
        } else {
          showUnsuccessfulFeedback(binding.errorFeedback, requireContext());
        }
      }

      @Override
      public void onFailure(@NonNull Call<Hit> call, @NonNull Throwable t) {
        hideProgressDialog(binding.progressBar, binding.progressMessage);
        showFailureFeedback(binding.errorFeedback, requireContext());
        Log.e(TAG, "Error while fetching recipe with ID: " + recipeId, t);
      }
    });
  }

  private void setRecipeDetails(Recipe recipe){
    // Setup action bar title
    ActionBar actionBar = Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar());
    actionBar.setTitle(recipe.getLabel());

    Glide.with(requireContext()).asBitmap().load(recipe.getImages().getRegular().getUrl()).placeholder(R.drawable.brunch_dining).into(binding.recipeImage);
    binding.recipeLabel.setText(recipe.getLabel());
    binding.recipeSource.setText(recipe.getSource());

    // Add listener to recipe source to open website
    openWebsite(recipe.getUrl());

    // Set calories and serving quantity
    binding.caloriesQuantity.setText(String.format(Locale.ENGLISH, "%.2f", recipe.getCalories()));
    binding.yieldQuantity.setText(String.format(Locale.ENGLISH, "%d", recipe.getYield()));

    // Set ingredient list
    binding.ingredientList.setLayoutManager(new LinearLayoutManager(requireContext()));
    binding.ingredientList.setAdapter(new IngredientListAdapter(requireContext(), recipe.getIngredients()));

    // Set recipe type labels: meal type, dish type, cuisine type
    for(String mealType: recipe.getMealType()){
      Chip chip = new Chip(requireContext());
      chip.setText(mealType);
      binding.recipeTypeLabels.addView(chip);
    }

    for (String dishType: recipe.getDishType()){
      Chip chip = new Chip(requireContext());
      chip.setText(dishType);
      binding.recipeTypeLabels.addView(chip);
    }

    for(String cuisineType: recipe.getCuisineType()){
      Chip chip = new Chip(requireContext());
      chip.setText(cuisineType);
      binding.recipeTypeLabels.addView(chip);
    }

    // Set recipe diet and health labels
    for(String dietLabel: recipe.getDietLabels()){
      Chip chip = new Chip(requireContext());
      chip.setText(dietLabel);
      binding.recipeDietHealthLabels.addView(chip);
    }

    for (String healthLabel: recipe.getHealthLabels()){
      Chip chip = new Chip(requireContext());
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

    // Set up save button
    setUpSaveButton(recipe);
  }

  private void openWebsite(String websiteUrl){
    binding.recipeSource.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
      requireContext().startActivity(intent);
    });
  }

  private void saveRecipe(Recipe recipe) {
    String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    DatabaseReference recipeReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPE_LOCATION).child(userId).child(recipeId);
    recipe.setId(recipeId);
    recipeReference.setValue(recipe);
    Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}