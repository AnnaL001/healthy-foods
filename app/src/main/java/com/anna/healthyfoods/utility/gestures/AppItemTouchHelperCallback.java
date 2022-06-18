package com.anna.healthyfoods.utility.gestures;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class AppItemTouchHelperCallback extends ItemTouchHelper.Callback {
  private final AppItemTouchHelper itemTouchHelper;

  public AppItemTouchHelperCallback(AppItemTouchHelper itemTouchHelper) {
    this.itemTouchHelper = itemTouchHelper;
  }

  // Inform ItemTouchHelper of supported movement directions; Drag directions & Swipe directions
  @Override
  public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
    return makeMovementFlags(dragFlags, swipeFlags);
  }

  // Notify adapter that an item has been moved thus update item's new positioning
  @Override
  public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
    if(viewHolder.getItemViewType() != target.getItemViewType()){
      return false;
    }
    itemTouchHelper.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    return true;
  }

  // Notify adapter that an item has been swiped and thus delete item in list
  @Override
  public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
    itemTouchHelper.onItemSwiped(viewHolder.getAdapterPosition());
  }
}
