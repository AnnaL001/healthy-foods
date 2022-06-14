package com.anna.healthyfoods;

import static org.junit.Assert.assertEquals;

import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.Objects;

@RunWith(RobolectricTestRunner.class)
public class SignupActivityTest {
  private SignupActivity activity;
  
  @Before
  public void setUp()  {
    activity = Robolectric.buildActivity(SignupActivity.class)
            .create()
            .start()
            .resume()
            .get();
  }

  @Test
  public void validateTextViewContent() {
    TextView gettingStartedTextView = activity.findViewById(R.id.getting_started_text);
    TextView signUpAdditionalTextView = activity.findViewById(R.id.signup_additional_text);
    assertEquals(activity.getString(R.string.getting_started), gettingStartedTextView.getText());
    assertEquals(activity.getString(R.string.signup_additional_text), signUpAdditionalTextView.getText());
  }

  @Test
  public void validateEmailTextInputLayout() {
    TextInputLayout emailTextInputLayout = activity.findViewById(R.id.email_text_input_layout);
    assertEquals(activity.getString(R.string.email), Objects.requireNonNull(emailTextInputLayout.getEditText()).getHint());
  }

  @Test
  public void validatePasswordTextInputLayout() {
    TextInputLayout passwordTextInputLayout = activity.findViewById(R.id.password_text_input_layout);
    assertEquals(activity.getString(R.string.password), Objects.requireNonNull(passwordTextInputLayout.getEditText()).getHint());
  }

  @Test
  public void validateConfirmPasswordTextInputLayout() {
    TextInputLayout confirmPasswordTextInputLayout = activity.findViewById(R.id.confirm_password_text_input_layout);
    assertEquals(activity.getString(R.string.confirm_password), Objects.requireNonNull(confirmPasswordTextInputLayout.getEditText()).getHint());
  }

  @Test
  public void validateButton() {
    Button button = activity.findViewById(R.id.btn_signup);
    assertEquals(activity.getString(R.string.sign_up), button.getText());
  }
}
