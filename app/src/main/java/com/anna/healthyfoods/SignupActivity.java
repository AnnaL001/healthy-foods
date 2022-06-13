package com.anna.healthyfoods;

import static com.anna.healthyfoods.utility.UserInterfaceHelpers.hideProgressDialog;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showProgressDialog;
import static com.anna.healthyfoods.utility.Validator.validateConfirmPasswordInput;
import static com.anna.healthyfoods.utility.Validator.validateEmailInput;
import static com.anna.healthyfoods.utility.Validator.validatePasswordInput;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anna.healthyfoods.databinding.ActivitySignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
  public static final String TAG = SignupActivity.class.getSimpleName();
  private ActivitySignupBinding binding;
  private FirebaseAuth auth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySignupBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setUpLinkToLogin();

    auth = FirebaseAuth.getInstance();

    binding.btnSignup.setOnClickListener(view -> signUpUser(
            Objects.requireNonNull(binding.emailTextInputLayout.getEditText()).getText().toString(),
            Objects.requireNonNull(binding.passwordTextInputLayout.getEditText()).getText().toString(),
            Objects.requireNonNull(binding.confirmPasswordTextInputLayout.getEditText()).getText().toString()
    ));
  }

  private void setUpLinkToLogin(){
    // Add an underline to text
    binding.linkToLogin.setPaintFlags(binding.linkToLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    binding.linkToLogin.setOnClickListener(view -> redirectToLogin());
  }

  // Register a user
  private void signUpUser(String email, String password, String confirmPassword) {
    if(validateCredentials(email, password, confirmPassword)) {
      showProgressDialog(binding.signupProgressBar, binding.progressMessage);

      auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, signUpTask -> {
        hideProgressDialog(binding.signupProgressBar, binding.progressMessage);
        if(signUpTask.isSuccessful()){
          // Display feedback to user
          Log.d(TAG, "Registration successful");
          verifyUser(Objects.requireNonNull(auth.getCurrentUser()));
          // Sign out user because of automatic Firebase login upon sign up & to allow for checking of email verification
          FirebaseAuth.getInstance().signOut();
          redirectToLogin();
        } else {
          // Display feedback to user
          Toast.makeText(this, getString(R.string.failed_registration) , Toast.LENGTH_SHORT).show();
          Log.e(TAG, "Registration failed", signUpTask.getException());
        }
      });
    }
  }

  // Verify user
  private void verifyUser(FirebaseUser user) {
    user.sendEmailVerification().addOnCompleteListener(this, verifyTask -> {
      if(verifyTask.isSuccessful()){
        Toast.makeText(this, getString(R.string.verification_prompt) , Toast.LENGTH_SHORT).show();
        Log.d(TAG, "User successfully verified");
      } else {
        Log.e(TAG, "Sending email verification failed", verifyTask.getException());
      }
    });
  }

  private void redirectToLogin(){
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }


  // Combine email, password and confirm password validation and return result; true/false
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