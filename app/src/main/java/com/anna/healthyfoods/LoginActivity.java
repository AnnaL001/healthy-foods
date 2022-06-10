package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.anna.healthyfoods.databinding.ActivityLoginBinding;
import com.anna.healthyfoods.models.Ingredient;

public class LoginActivity extends AppCompatActivity {
  private ActivityLoginBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setUpLinkToSignUp();
  }

  private void setUpLinkToSignUp(){
    binding.linkToSignup.setPaintFlags(binding.linkToSignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    binding.linkToSignup.setOnClickListener(view -> {
      // Redirect to sign up
    });
  }

}