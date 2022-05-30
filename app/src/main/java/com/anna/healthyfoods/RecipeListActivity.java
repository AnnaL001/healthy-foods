package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.anna.healthyfoods.adapter.RecipeListAdapter;
import com.anna.healthyfoods.databinding.ActivityRecipeListBinding;

import java.util.Objects;

public class RecipeListActivity extends AppCompatActivity {
  private ActivityRecipeListBinding binding;
  private final String[] recipeTitles = {"Chicken Vesuvio", "Green Bean Casserole Pie Recipe", "Baked Sweet Potatoes and Pecans", "Vegetable Lasagna", "Fresh tuna tortillas"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityRecipeListBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    String title = getString(getIntent().getIntExtra("meal_type", R.string.breakfast));
    Log.d("RecipeListActivity", title);

    Objects.requireNonNull(this.getSupportActionBar()).setTitle(title);

    initializeAdapter();
    initializeOnClickListener();
  }

  private void initializeAdapter(){
    RecipeListAdapter adapter = new RecipeListAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, recipeTitles);
    binding.recipeListView.setAdapter(adapter);
  }

  private void initializeOnClickListener(){
    binding.recipeListView.setOnItemClickListener((adapterView, view, i, l) -> {
      String recipe = ((TextView)view).getText().toString();
      Toast.makeText(getApplicationContext(), recipe, Toast.LENGTH_LONG).show();
    });
  }
}