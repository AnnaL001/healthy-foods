package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.anna.healthyfoods.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
  public static final String TAG = MainActivity.class.getSimpleName();
  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    handleButtonClick();
  }

  private void handleButtonClick(){
    binding.btnGetStarted.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserDetailsActivity.class)));
    Log.i(TAG, "Navigating to UserDetailsActivity ...");
  }
}