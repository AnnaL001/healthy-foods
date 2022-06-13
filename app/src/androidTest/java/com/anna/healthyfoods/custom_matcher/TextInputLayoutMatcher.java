package com.anna.healthyfoods.custom_matcher;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class TextInputLayoutMatcher {
  public static Matcher<View> hasDisplayedErrorText(final String expectedErrorText) {
    return new TypeSafeMatcher<View>() {

      @Override
      public boolean matchesSafely(View view) {
        if (!(view instanceof TextInputLayout)) {
          return false;
        }
        CharSequence errorText = ((TextInputLayout) view).getError();

        if (errorText == null) {
          return false;
        }

        String actualErrorText = errorText.toString();

        return expectedErrorText.equals(actualErrorText);
      }

      @Override
      public void describeTo(Description description) {
      }
    };
  }
}
