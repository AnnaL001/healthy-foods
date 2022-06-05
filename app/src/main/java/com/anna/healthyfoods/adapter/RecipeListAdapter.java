package com.anna.healthyfoods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anna.healthyfoods.databinding.ItemRecipeListBinding;
import com.anna.healthyfoods.models.Hit;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {
  private final Context context;
  private final List<Hit> hits;

  public RecipeListAdapter(Context context, List<Hit> hits) {
    this.context = context;
    this.hits = hits;
  }

  @NonNull
  @Override
  public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new RecipeListViewHolder(ItemRecipeListBinding.inflate(LayoutInflater.from(context), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull RecipeListViewHolder holder, int position) {
    holder.bindRecipe(hits.get(position));
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

    private void bindRecipe(Hit hit) {
      Glide.with(context).asBitmap().load(hit.getRecipe().getImages().getThumbnail().getUrl()).into(binding.recipeImageView);
      binding.recipeLabel.setText(hit.getRecipe().getLabel());
      binding.recipeSource.setText(hit.getRecipe().getSource());
    }
  }
}
