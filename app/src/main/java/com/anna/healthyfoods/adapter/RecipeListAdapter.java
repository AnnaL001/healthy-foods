package com.anna.healthyfoods.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

public class RecipeListAdapter extends ArrayAdapter {
  private Context context;
  private String[] recipeTitles;
  private int no = 1;

  public RecipeListAdapter(@NonNull Context context, int resource, String[] recipeTitles) {
    super(context, resource);
    this.context = context;
    this.recipeTitles = recipeTitles;
  }

  @Override
  public int getCount() {
    return recipeTitles.length;
  }

  @Nullable
  @Override
  public Object getItem(int position) {
    String recipeTitle = recipeTitles[position];
    return String.format(Locale.ENGLISH,"%d. %s", no++, recipeTitle);
  }
}
