package com.anna.healthyfoods;

import static com.anna.healthyfoods.utility.Validator.validateEmailInput;
import static com.anna.healthyfoods.utility.Validator.validatePasswordInput;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anna.healthyfoods.databinding.ActivityLoginBinding;

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
    // Add an underline to text
    binding.linkToSignup.setPaintFlags(binding.linkToSignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    binding.linkToSignup.setOnClickListener(view -> {
      startActivity(new Intent(getApplicationContext(), SignupActivity.class));
      finish();
    });
  }

  private void redirectToUserDetails(){
    Intent intent = new Intent(getApplicationContext(), UserDetailsActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }

  private boolean validateCredentials(String email, String password) {
    return isEmailValid(email) && isPasswordValid(password);
  }

  // Validate email
  private boolean isEmailValid(String email) {
    boolean isValid = false;

    if(!validateEmailInput(email)){
      binding.emailTextInputLayout.setError(getString(R.string.required_valid_email_error));
    } else {
      isValid = true;
      binding.emailTextInputLayout.setErrorEnabled(false);
    }

    return isValid;
  }

  // Validate password input
  private boolean isPasswordValid(String password) {
    boolean isValid = false;

    if(!validatePasswordInput(password)){
      binding.passwordTextInputLayout.setError(getString(R.string.required_valid_password_error));
    } else {
      isValid = true;
      binding.passwordTextInputLayout.setErrorEnabled(false);
    }

    return isValid;
  }
}