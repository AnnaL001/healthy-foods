package com.anna.healthyfoods;

import static com.anna.healthyfoods.utility.UserInterfaceHelpers.hideProgressBar;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showProgressBar;
import static com.anna.healthyfoods.utility.Validator.validateEmailInput;
import static com.anna.healthyfoods.utility.Validator.validatePasswordInput;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anna.healthyfoods.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
  public static final String TAG = LoginActivity.class.getSimpleName();
  private ActivityLoginBinding binding;
  private FirebaseAuth auth;
  private FirebaseAuth.AuthStateListener authStateListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    auth = FirebaseAuth.getInstance();
    initializeAuthStateListener();

    setUpLinkToSignUp();

    // Login user upon button click
    binding.btnLogin.setOnClickListener(view -> loginUser(
          Objects.requireNonNull(binding.emailTextInputLayout.getEditText()).getText().toString(),
          Objects.requireNonNull(binding.passwordTextInputLayout.getEditText()).getText().toString()
    ));
  }

  private void setUpLinkToSignUp(){
    // Add an underline to text
    binding.linkToSignup.setPaintFlags(binding.linkToSignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    binding.linkToSignup.setOnClickListener(view -> {
      startActivity(new Intent(getApplicationContext(), SignupActivity.class));
      finish();
    });
  }

  private void loginUser(String email, String password) {
    if(validateCredentials(email, password)) {
      showProgressBar(binding.loginProgressBar);

      auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, loginTask -> {
        hideProgressBar(binding.loginProgressBar);

        // Only login user if there email is verified
          if(loginTask.isSuccessful()){
            if(Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()){
              Toast.makeText(getApplicationContext(), getString(R.string.successful_authentication), Toast.LENGTH_SHORT).show();
              redirectToUserDetails();
              Log.d(TAG, "Authentication successful");
            } else {
              Toast.makeText(getApplicationContext(), getString(R.string.email_verification_prompt), Toast.LENGTH_SHORT).show();
              Log.d(TAG, "Email verification toast displayed");
            }
          } else {
            Toast.makeText(getApplicationContext(), getString(R.string.failed_authentication), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Authentication failed");
          }
      });
    }
  }

  private void redirectToHome(){
    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
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

  private void initializeAuthStateListener() {
    authStateListener = firebaseAuth -> {
      if(firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified()){
        redirectToUserDetails();
      }
    };
  }

  @Override
  protected void onStart() {
    super.onStart();
    auth.addAuthStateListener(authStateListener);
  }

  @Override
  protected void onStop() {
    if(authStateListener != null){
      auth.removeAuthStateListener(authStateListener);
    }
    super.onStop();
  }
}