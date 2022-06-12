package com.anna.healthyfoods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.databinding.ItemRecipeListBinding;
import com.anna.healthyfoods.interfaces.ItemOnClickListener;
import com.anna.healthyfoods.models.Hit;
import com.anna.healthyfoods.models.Recipe;
import com.bumptech.glide.Glide;


import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {
  private final Context context;
  private final List<Hit> hits;
  private final ItemOnClickListener listener;

  public RecipeListAdapter(Context context, List<Hit> hits, ItemOnClickListener listener) {
    this.context = context;
    this.hits = hits;
    this.listener = listener;
  }

  @NonNull
  @Override
  public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new RecipeListViewHolder(ItemRecipeListBinding.inflate(LayoutInflater.from(context), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull RecipeListViewHolder holder, int position) {
    holder.bindRecipe(hits.get(position).getRecipe());
    holder.binding.getRoot().setOnClickListener(view -> {
      String uri = hits.get(position).getRecipe().getUri();
      // Extract recipe ID from recipe's URI
      String recipeId = uri.substring(uri.indexOf("#") + 1);
      listener.onClick(recipeId);
    });
  }

  @Override
  public int getItemCount() {
    return hits.size();
  }

  public static class RecipeListViewHolder extends RecyclerView.ViewHolder {
    private final ItemRecipeListBinding binding;
    private final Context context;

    public RecipeListViewHolder(ItemRecipeListBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      this.context = binding.getRoot().getContext();
    }

    private void bindRecipe(Recipe recipe) {
      Glide.with(context).asBitmap().load(recipe.getImages().getThumbnail().getUrl()).placeholder(R.drawable.brunch_dining).into(binding.recipeImageView);
      binding.recipeLabel.setText(recipe.getLabel());
      binding.recipeSource.setText(recipe.getSource());
    }
  }
}
