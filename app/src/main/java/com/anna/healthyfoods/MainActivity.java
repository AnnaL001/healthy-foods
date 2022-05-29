package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.anna.healthyfoods.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
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
  }
}