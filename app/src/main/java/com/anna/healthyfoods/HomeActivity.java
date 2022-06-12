package com.anna.healthyfoods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.util.Log;

import com.anna.healthyfoods.adapter.HomeViewPagerAdapter;
import com.anna.healthyfoods.databinding.ActivityHomeBinding;
import com.anna.healthyfoods.models.Settings;
import com.anna.healthyfoods.ui.MealTypeFragment;
import com.anna.healthyfoods.ui.SearchFragment;
import com.anna.healthyfoods.utility.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
  public static final String TAG = HomeActivity.class.getSimpleName();
  private ActivityHomeBinding binding;
  private Settings userSettings;
  private FragmentStateAdapter viewPagerAdapter;
  private DatabaseReference settingsReference;
  private ValueEventListener settingsListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityHomeBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    settingsReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SETTINGS_LOCATION).child(userId);

    setUpHomeScreen();
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

  private void setUpHomeScreen(){
    settingsListener = settingsReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        userSettings = snapshot.getValue(Settings.class);
        viewPagerAdapter = new HomeViewPagerAdapter(HomeActivity.this, userSettings);
        binding.viewPager.setAdapter(viewPagerAdapter);
        initializeTabLayout();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.e(TAG, "Error while fetching user settings", error.toException());
      }
    });
  }

  @Override
  protected void onDestroy() {
    settingsReference.removeEventListener(settingsListener);
    super.onDestroy();
  }
}