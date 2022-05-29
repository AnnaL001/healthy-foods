package com.anna.healthyfoods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.anna.healthyfoods.databinding.ItemMealTypeBinding;
import com.anna.healthyfoods.interfaces.ItemOnClickListener;

public class MealTypeAdapter extends BaseAdapter {
  private final Context context;
  private final int[] mealTypeImages;
  private final int[] mealTypeTitles;
  private final ItemOnClickListener listener;

  public MealTypeAdapter(Context context, int[] mealTypeImages, int[] mealTypeTitles, ItemOnClickListener listener) {
    this.context = context;
    this.mealTypeImages = mealTypeImages;
    this.mealTypeTitles = mealTypeTitles;
    this.listener = listener;
  }

  @Override
  public int getCount() {
    return mealTypeTitles.length;
  }

  @Override
  public Object getItem(int i) {
    return null;
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    View gridView;
    if(view == null){
      ItemMealTypeBinding binding = ItemMealTypeBinding.inflate(LayoutInflater.from(context));
      // Get layout from xml
      gridView = binding.getRoot();
      binding.mealTypeImage.setImageResource(mealTypeImages[i]);
      binding.mealTypeLabel.setText(mealTypeTitles[i]);
      // Set OnClickListener
      binding.mealTypeImage.setOnClickListener(imageView -> listener.onClick());
    } else {
      gridView = view;
    }
    return gridView;
  }
}
