package com.anna.healthyfoods.ui;

import static com.anna.healthyfoods.utility.UserInterfaceHelpers.hideProgressDialog;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showProgressDialog;
import static com.anna.healthyfoods.utility.Validator.validateConfirmPasswordInput;
import static com.anna.healthyfoods.utility.Validator.validateEmailInput;
import static com.anna.healthyfoods.utility.Validator.validatePasswordInput;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anna.healthyfoods.R;
import com.anna.healthyfoods.databinding.FragmentSignupBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.concurrent.Executor;

public class SignupFragment extends Fragment {
  public static final String TAG = SignupFragment.class.getSimpleName();
  private FragmentSignupBinding binding;
  private FirebaseAuth auth;

  public SignupFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentSignupBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setUpLinkToLogin();

    FirebaseApp.initializeApp(requireContext());
    auth = FirebaseAuth.getInstance();

    binding.btnSignup.setOnClickListener(signupBtn -> signUpUser(
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

      auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Executor) this, signUpTask -> {
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
          Toast.makeText(requireContext(), getString(R.string.failed_registration) , Toast.LENGTH_SHORT).show();
          Log.e(TAG, "Registration failed", signUpTask.getException());
        }
      });
    }
  }

  // Verify user
  private void verifyUser(FirebaseUser user) {
    user.sendEmailVerification().addOnCompleteListener((Executor) this, verifyTask -> {
      if(verifyTask.isSuccessful()){
        Toast.makeText(requireContext(), getString(R.string.verification_prompt) , Toast.LENGTH_SHORT).show();
        Log.d(TAG, "User successfully verified");
      } else {
        Log.e(TAG, "Sending email verification failed", verifyTask.getException());
      }
    });
  }

  private void redirectToLogin(){
    requireActivity().getSupportFragmentManager().beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_container, new LoginFragment())
            .commit();
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

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}