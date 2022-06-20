package com.anna.healthyfoods.viewholder;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.databinding.ItemSavedRecipeListBinding;
import com.anna.healthyfoods.models.Recipe;
import com.bumptech.glide.Glide;

public class FirebaseRecipeViewHolder extends RecyclerView.ViewHolder {
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
  }
}
