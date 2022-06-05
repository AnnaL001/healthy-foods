package com.anna.healthyfoods.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.anna.healthyfoods.models.Settings;
import com.anna.healthyfoods.ui.MealTypeFragment;

public class HomeViewPagerAdapter extends FragmentStateAdapter {
  private static final int NUM_PAGES = 3;
  private Settings userSettings;

  public HomeViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Settings userSettings) {
    super(fragmentActivity);
    this.userSettings = userSettings;
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    Fragment fragment = new Fragment();
    switch (position){
      case 0:
        fragment = MealTypeFragment.newInstance(userSettings);
        break;
    }
    return fragment;
  }

  @Override
  public int getItemCount() {
    return NUM_PAGES;
  }
}
