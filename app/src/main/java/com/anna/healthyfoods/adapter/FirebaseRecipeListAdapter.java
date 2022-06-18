package com.anna.healthyfoods.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.anna.healthyfoods.databinding.ItemSavedRecipeListBinding;
import com.anna.healthyfoods.models.Recipe;
import com.anna.healthyfoods.utility.gestures.AppItemTouchHelper;
import com.anna.healthyfoods.utility.gestures.OnTouchScreenDragListener;
import com.anna.healthyfoods.viewholder.FirebaseRecipeViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class FirebaseRecipeListAdapter extends FirebaseRecyclerAdapter<Recipe, FirebaseRecipeViewHolder> implements AppItemTouchHelper {
  private final DatabaseReference databaseReference;
  private final OnTouchScreenDragListener dragListener;
  private final Context context;

  public FirebaseRecipeListAdapter(@NonNull FirebaseRecyclerOptions<Recipe> options, DatabaseReference databaseReference, OnTouchScreenDragListener dragListener, Context context) {
    super(options);
    this.databaseReference = databaseReference.getRef();
    this.dragListener = dragListener;
    this.context = context;
  }

  // TO FIND A BETTER SOLUTION
  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onBindViewHolder(@NonNull FirebaseRecipeViewHolder holder, int position, @NonNull Recipe recipe) {
    holder.bindRecipe(recipe);

    // Add TouchListener to listen for drag events when user touches drag icon
    holder.binding.iconDrag.setOnTouchListener((view, motionEvent) -> {
      if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN){
        dragListener.onDrag(holder);
      }
      return true;
    });
  }

  @NonNull
  @Override
  public FirebaseRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new FirebaseRecipeViewHolder(ItemSavedRecipeListBinding.inflate(LayoutInflater.from(context), parent, false));
  }


  @Override
  public boolean onItemMoved(int startPosition, int endPosition) {
    return false;
  }

  @Override
  public void onItemSwiped(int position) {

  }
}
