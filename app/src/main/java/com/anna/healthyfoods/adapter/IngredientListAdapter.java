package com.anna.healthyfoods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.databinding.ItemIngredientListBinding;
import com.anna.healthyfoods.models.Ingredient;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientListViewHolder> {
  private final Context context;
  private final List<Ingredient> ingredients;

  public IngredientListAdapter(Context context, List<Ingredient> ingredients) {
    this.context = context;
    this.ingredients = ingredients;
  }

  @NonNull
  @Override
  public IngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new IngredientListViewHolder(ItemIngredientListBinding.inflate(LayoutInflater.from(context), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull IngredientListViewHolder holder, int position) {
    holder.bindIngredient(ingredients.get(position));
  }

  @Override
  public int getItemCount() {
    return ingredients.size();
  }

  public static class IngredientListViewHolder extends RecyclerView.ViewHolder {
    private final ItemIngredientListBinding binding;
    private final Context context;

    public IngredientListViewHolder(ItemIngredientListBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      this.context = binding.getRoot().getContext();
    }

    private void bindIngredient(Ingredient ingredient) {
      Glide.with(context).asBitmap().load(ingredient.getImage()).placeholder(R.drawable.brunch_dining).into(binding.ingredientImageView);
      binding.ingredientLabel.setText(ingredient.getText());
      binding.ingredientQuantity.setText(String.format(Locale.ENGLISH, "%.2f g", ingredient.getWeight()));
    }
  }
}
