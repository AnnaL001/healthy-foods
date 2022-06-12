package com.anna.healthyfoods;

import static com.anna.healthyfoods.utility.Validator.validateConfirmPasswordInput;
import static com.anna.healthyfoods.utility.Validator.validateEmailInput;
import static com.anna.healthyfoods.utility.Validator.validatePasswordInput;

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

    binding.btnSignup.setOnClickListener(view -> {

    });
  }

  private void setUpLinkToLogin(){
    // Add an underline to text
    binding.linkToLogin.setPaintFlags(binding.linkToLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    binding.linkToLogin.setOnClickListener(view -> redirectToLogin());
  }

  private void redirectToLogin(){
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }

  private boolean validateCredentials(String email, String password, String confirmPassword) {
    return isEmailValid(email) && isPasswordValid(password) && isConfirmPasswordValid(password, confirmPassword);
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

  // Validate confirm password
  private boolean isConfirmPasswordValid(String password, String confirmPassword) {
    boolean isValid = false;

    if(!validateConfirmPasswordInput(password, confirmPassword)){
      binding.confirmPasswordTextInputLayout.setError(getString(R.string.required_confirm_password_error));
    } else {
      isValid = true;
      binding.confirmPasswordTextInputLayout.setErrorEnabled(false);
    }

    return isValid;
  }

}