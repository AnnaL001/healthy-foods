package com.anna.healthyfoods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anna.healthyfoods.databinding.ItemMealTypeBinding;
import com.bumptech.glide.Glide;

import java.util.List;

public class MealTypeAdapter extends RecyclerView.Adapter<MealTypeAdapter.MealTypeViewHolder> {
  private Context context;
  private List<Integer> mealTypeImages;
  private List<String> mealTypeLabels;

  public MealTypeAdapter(Context context, List<Integer> mealTypeImages, List<String> mealTypeLabels) {
    this.context = context;
    this.mealTypeImages = mealTypeImages;
    this.mealTypeLabels = mealTypeLabels;
  }

  @NonNull
  @Override
  public MealTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new MealTypeViewHolder(ItemMealTypeBinding.inflate(LayoutInflater.from(context), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull MealTypeViewHolder holder, int position) {
    holder.bindMealType(mealTypeLabels.get(position), mealTypeImages.get(position));
  }

  @Override
  public int getItemCount() {
    return mealTypeLabels.size();
  }

  static class MealTypeViewHolder extends RecyclerView.ViewHolder {
    private ItemMealTypeBinding binding;
    private Context context;

    public MealTypeViewHolder(ItemMealTypeBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      context = binding.getRoot().getContext();
    }

    public void bindMealType(String mealTypeLabel, int mealTypeImage){
      Glide.with(context).asDrawable().load(mealTypeImage).into(binding.mealTypeImageView);
      binding.mealTypeLabel.setText(mealTypeLabel);
    }
  }
}
