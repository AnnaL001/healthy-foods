package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

import com.anna.healthyfoods.adapter.HomeViewPagerAdapter;
import com.anna.healthyfoods.databinding.ActivityHomeBinding;
import com.anna.healthyfoods.models.Settings;
import com.anna.healthyfoods.ui.MealTypeFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import org.parceler.Parcels;

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
  }

}