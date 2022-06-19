package com.anna.healthyfoods.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anna.healthyfoods.RecipeListActivity;
import com.anna.healthyfoods.databinding.ItemMealTypeBinding;
import com.anna.healthyfoods.models.Settings;
import com.anna.healthyfoods.utility.Constants;
import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;

public class MealTypeAdapter extends RecyclerView.Adapter<MealTypeAdapter.MealTypeViewHolder> {
  private final Context context;
  private final List<Integer> mealTypeImages;
  private final List<String> mealTypeLabels;
  private final Settings userSettings;

  public MealTypeAdapter(Context context, List<Integer> mealTypeImages, List<String> mealTypeLabels, Settings userSettings) {
    this.context = context;
    this.mealTypeImages = mealTypeImages;
    this.mealTypeLabels = mealTypeLabels;
    this.userSettings = userSettings;
  }

  @NonNull
  @Override
  public MealTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new MealTypeViewHolder(ItemMealTypeBinding.inflate(LayoutInflater.from(context), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull MealTypeViewHolder holder, int position) {
    holder.bindMealType(mealTypeLabels.get(position), mealTypeImages.get(position));
    holder.binding.getRoot().setOnClickListener(view -> {
      Intent intent = new Intent(context, RecipeListActivity.class);
      intent.putExtra(Constants.EXTRA_MEAL_TYPE, mealTypeLabels.get(position));
      intent.putExtra(Constants.EXTRA_USER_SETTINGS, Parcels.wrap(userSettings));
      context.startActivity(intent);
    });
  }

  @Override
  public int getItemCount() {
    return mealTypeLabels.size();
  }

  static class MealTypeViewHolder extends RecyclerView.ViewHolder {
    private final ItemMealTypeBinding binding;
    private final Context context;

    public MealTypeViewHolder(ItemMealTypeBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      context = binding.getRoot().getContext();
    }

    private void bindMealType(String mealTypeLabel, int mealTypeImage){
      Glide.with(context).asDrawable().load(mealTypeImage).into(binding.mealTypeImageView);
      binding.mealTypeLabel.setText(mealTypeLabel);
    }
  }
}
