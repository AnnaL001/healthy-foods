package com.anna.healthyfoods;

import static org.junit.Assert.*;

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
public class LoginActivityTest {
  private LoginActivity activity;

  @Before
  public void setUp()  {
    activity = Robolectric.buildActivity(LoginActivity.class)
            .create()
            .start()
            .resume()
            .get();
  }

  @Test
  public void validateTextViewContent() {
    TextView loginText = activity.findViewById(R.id.login_text);
    TextView loginAdditionalTextView = activity.findViewById(R.id.login_additional_text);
    assertEquals(activity.getString(R.string.login_account), loginText.getText());
    assertEquals(activity.getString(R.string.login_additional_text), loginAdditionalTextView.getText());
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
  public void validateButton() {
    Button button = activity.findViewById(R.id.btn_login);
    assertEquals(activity.getString(R.string.login), button.getText());
  }
}
