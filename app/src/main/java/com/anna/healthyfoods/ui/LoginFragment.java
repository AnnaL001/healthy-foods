package com.anna.healthyfoods.ui;

import static com.anna.healthyfoods.utility.UserInterfaceHelpers.hideProgressDialog;
import static com.anna.healthyfoods.utility.UserInterfaceHelpers.showProgressDialog;
import static com.anna.healthyfoods.utility.Validator.validateEmailInput;
import static com.anna.healthyfoods.utility.Validator.validatePasswordInput;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anna.healthyfoods.HomeActivity;
import com.anna.healthyfoods.R;
import com.anna.healthyfoods.UserDetailsActivity;
import com.anna.healthyfoods.databinding.FragmentLoginBinding;
import com.anna.healthyfoods.models.Settings;
import com.anna.healthyfoods.utility.Constants;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.Executor;


public class LoginFragment extends Fragment {
  public static final String TAG = LoginFragment.class.getSimpleName();
  private FragmentLoginBinding binding;
  private FirebaseAuth auth;
  private FirebaseAuth.AuthStateListener authStateListener;
  private ValueEventListener settingsListener;
  private DatabaseReference settingsReference;

  public LoginFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentLoginBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    FirebaseApp.initializeApp(requireContext());
    auth = FirebaseAuth.getInstance();
    initializeAuthStateListener();

    setUpLinkToSignUp();

    // Login user upon button click
    setUpLoginButton();
  }

  private void setUpLoginButton(){
    binding.btnLogin.setOnClickListener(view -> loginUser(
            Objects.requireNonNull(binding.emailTextInputLayout.getEditText()).getText().toString(),
            Objects.requireNonNull(binding.passwordTextInputLayout.getEditText()).getText().toString()
    ));
  }

  private void setUpLinkToSignUp(){
    // Add an underline to text
    binding.linkToSignup.setPaintFlags(binding.linkToSignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    binding.linkToSignup.setOnClickListener(view -> requireActivity().getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out
            )
            .add(R.id.fragment_container, new SignupFragment())
            .commit());
  }

  private void loginUser(String email, String password) {
    if(validateCredentials(email, password)) {
      showProgressDialog(binding.loginProgressBar, binding.progressMessage);

      auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), loginTask -> {
        hideProgressDialog(binding.loginProgressBar, binding.progressMessage);

        // Only login user if there email is verified
        if(loginTask.isSuccessful()){
          if(Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()){
            Toast.makeText(getContext(), getString(R.string.successful_authentication), Toast.LENGTH_SHORT).show();
            redirectToNextScreen();
            Log.d(TAG, "Authentication successful");
          } else {
            Toast.makeText(getContext(), getString(R.string.email_verification_prompt), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Email verification toast displayed");
          }
        } else {
          Toast.makeText(getContext(), getString(R.string.failed_authentication), Toast.LENGTH_SHORT).show();
          Log.d(TAG, "Authentication failed");
        }
      });
    }
  }

  private void redirectToHome(){
    Intent intent = new Intent(getContext(), HomeActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    requireActivity().startActivity(intent);
    requireActivity().finish();
  }

  private void redirectToUserDetails(){
    Intent intent = new Intent(getContext(), UserDetailsActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    requireActivity().startActivity(intent);
    requireActivity().finish();
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
        redirectToNextScreen();
      }
    };
  }

  private void redirectToNextScreen() {
    String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
    settingsReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SETTINGS_LOCATION).child(userId);
    settingsListener = settingsReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        Settings userSettings = snapshot.getValue(Settings.class);
        if(userSettings != null){
          redirectToHome();
        } else {
          redirectToUserDetails();
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.e(TAG, "Error while fetching user settings: ", error.toException());
      }
    });
  }

  @Override
  public void onStart() {
    super.onStart();
    auth.addAuthStateListener(authStateListener);
  }

  @Override
  public void onStop() {
    super.onStop();
    if(authStateListener != null){
      auth.removeAuthStateListener(authStateListener);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
    if(settingsReference != null){
      settingsReference.removeEventListener(settingsListener);
    }
  }
}