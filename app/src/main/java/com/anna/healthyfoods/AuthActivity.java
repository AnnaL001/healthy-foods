package com.anna.healthyfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anna.healthyfoods.databinding.ActivityAuthBinding;
import com.anna.healthyfoods.ui.LoginFragment;
import com.anna.healthyfoods.ui.RecipeListFragment;
import com.anna.healthyfoods.ui.SignupFragment;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {
  private ActivityAuthBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAuthBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    inflateFragment();
  }

  private void inflateFragment(){
    getSupportFragmentManager().beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment_container, new LoginFragment())
            .commit();
  }
}