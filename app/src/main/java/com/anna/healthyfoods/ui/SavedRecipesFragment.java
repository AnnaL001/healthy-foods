package com.anna.healthyfoods.ui;

import static com.anna.healthyfoods.utility.UserInterfaceHelpers.hideProgressDialog;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showNoContentFound;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showRecipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.databinding.FragmentSavedRecipesBinding;
import com.anna.healthyfoods.databinding.ItemRecipeListBinding;
import com.anna.healthyfoods.models.Recipe;
import com.anna.healthyfoods.utility.Constants;
import com.anna.healthyfoods.viewholder.FirebaseRecipeViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SavedRecipesFragment extends Fragment {
  private FragmentSavedRecipesBinding binding;
  private FirebaseRecyclerAdapter<Recipe, FirebaseRecipeViewHolder> firebaseAdapter;
  private DatabaseReference recipeReference;

  public SavedRecipesFragment() {
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentSavedRecipesBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    recipeReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPE_LOCATION).child(userId);

    //For now, until database connection is added
    setUpFirebaseAdapter();
    hideProgressDialog(binding.progressBar, binding.progressMessage);
    showRecipes(binding.starredRecipeList);
  }

  private void setUpFirebaseAdapter(){
    // Set up FirebaseAdapter
    FirebaseRecyclerOptions<Recipe> options =
            new FirebaseRecyclerOptions.Builder<Recipe>()
                    .setQuery(recipeReference, Recipe.class)
                    .build();

    firebaseAdapter = new FirebaseRecyclerAdapter<Recipe, FirebaseRecipeViewHolder>(options) {

      @NonNull
      @Override
      public FirebaseRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FirebaseRecipeViewHolder(ItemRecipeListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
      }

      @Override
      protected void onBindViewHolder(@NonNull FirebaseRecipeViewHolder holder, int position, @NonNull Recipe recipe) {
        holder.bindRecipe(recipe);
      }
    };

    binding.starredRecipeList.setLayoutManager(new LinearLayoutManager(getContext()));
    if (firebaseAdapter.getItemCount() < 1) {
      showNoContentFound(binding.errorText, getString(R.string.no_saved_recipes));
    }
    binding.errorText.setVisibility(View.GONE);
    binding.starredRecipeList.setAdapter(firebaseAdapter);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  @Override
  public void onStart() {
    super.onStart();
    firebaseAdapter.startListening();
  }

  @Override
  public void onStop() {
    firebaseAdapter.stopListening();
    super.onStop();
  }
}
