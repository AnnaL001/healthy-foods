package com.anna.healthyfoods.viewholder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.RecipeDetailsActivity;
import com.anna.healthyfoods.databinding.ItemSavedRecipeListBinding;
import com.anna.healthyfoods.interfaces.ItemOnClickListener;
import com.anna.healthyfoods.models.Recipe;
import com.anna.healthyfoods.utility.Constants;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class FirebaseRecipeViewHolder extends RecyclerView.ViewHolder implements ItemOnClickListener {
  public static final String TAG = FirebaseRecipeViewHolder.class.getSimpleName();
  public final ItemSavedRecipeListBinding binding;
  private final Context context;

  public FirebaseRecipeViewHolder(ItemSavedRecipeListBinding binding) {
    super(binding.getRoot());
    this.binding = binding;
    this.context = binding.getRoot().getContext();
  }

  public void bindRecipe(Recipe recipe){
    Glide.with(context).asBitmap().load(recipe.getImages().getThumbnail().getUrl()).placeholder(R.drawable.brunch_dining).into(binding.recipeImageView);
    binding.recipeLabel.setText(recipe.getLabel());
    binding.recipeSource.setText(recipe.getSource());
    binding.getRoot().setOnClickListener(view -> this.onClick(recipe.getId()));
  }

  @Override
  public void onClick(String id) {
    String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    DatabaseReference recipeReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPE_LOCATION).child(userId).child(id);
    recipeReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        final String recipeId = Objects.requireNonNull(snapshot.getValue(Recipe.class)).getId();
        intent.putExtra("recipe_id", recipeId);
        context.startActivity(intent);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.e(TAG, "Error: ", error.toException());
      }
    });
  }
}
