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
import com.anna.healthyfoods.ui.RecipeDetailsFragment;
import com.anna.healthyfoods.ui.RecipeListFragment;
import com.anna.healthyfoods.utility.Constants;
import com.anna.healthyfoods.utility.UserInterfaceHelpers;

import org.parceler.Parcels;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity {
  public static final String TAG = RecipeListActivity.class.getSimpleName();
  private ActivityRecipeListBinding binding;
  private RecipeListAdapter adapter;
  private String mealType;
  private Settings userSettings;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityRecipeListBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    mealType = getIntent().getStringExtra(Constants.EXTRA_MEAL_TYPE);
    userSettings = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_USER_SETTINGS));

    inflateFragment();
  }

  private void inflateFragment(){
    getSupportFragmentManager().beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_container, RecipeListFragment.newInstance(mealType, userSettings))
            .commit();
  }
}