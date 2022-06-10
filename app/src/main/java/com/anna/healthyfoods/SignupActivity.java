package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.anna.healthyfoods.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
  private ActivitySignupBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySignupBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setUpLinkToLogin();
  }

  private void setUpLinkToLogin(){
    binding.linkToLogin.setPaintFlags(binding.linkToLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    binding.linkToLogin.setOnClickListener(view -> redirectToLogin());
  }

  private void redirectToLogin(){
    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    finish();
  }

  private void redirectToUserDetails(){
    Intent intent = new Intent(getApplicationContext(), UserDetailsActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }
}