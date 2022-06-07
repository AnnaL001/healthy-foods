package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

import com.anna.healthyfoods.adapter.HomeViewPagerAdapter;
import com.anna.healthyfoods.databinding.ActivityHomeBinding;
import com.anna.healthyfoods.models.Settings;
import com.anna.healthyfoods.ui.MealTypeFragment;
import com.anna.healthyfoods.ui.SearchFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.parceler.Parcels;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
  private ActivityHomeBinding binding;
  private Settings userSettings;
  private FragmentStateAdapter viewPagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityHomeBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    userSettings = Parcels.unwrap(getIntent().getParcelableExtra("userSettings"));
    viewPagerAdapter = new HomeViewPagerAdapter(this, userSettings);
    binding.viewPager.setAdapter(viewPagerAdapter);

    initializeTabLayout();
  }

  private void initializeTabLayout(){
    new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
      switch (position){
        case 0:
          tab.setText(getString(R.string.meal_types));
          break;
        case 1:
          tab.setText(getString(R.string.search));
          break;
        case 2:
          tab.setText(getString(R.string.starred));
          break;
      }
    }).attach();
  }

}