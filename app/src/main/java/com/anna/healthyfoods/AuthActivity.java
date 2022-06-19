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

  // Inflate LoginFragment
  private void inflateFragment(){
    getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out
            )
            .replace(R.id.fragment_container, new LoginFragment())
            .commit();
  }
}