package com.anna.healthyfoods.utility;

import android.content.Context;
import android.util.Patterns;

import com.anna.healthyfoods.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {
  // Returns false if form input values are empty and name is less than four characters
  public static boolean validateRequiredUserDetailsFormInput(String name, List<String> selectedDiets, List<String> selectedAllergies){
    return !name.isEmpty() && selectedDiets.size() != 0 && selectedAllergies.size() != 0;
  }

  // Returns false if name is less than four characters
  public static boolean validateNameInput(String name){
    return !(name.length() < 4 || name.matches(".*\\d.*"));
  }

  public static boolean validateEmailInput(String email) {
    return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  public static Map<String, Object> validatePasswordInput(Context context, String password) {
    Map<String, Object> result = new HashMap<>();
    result.put("valid", false);

    if(!(password.length() < 8)){
      result.put("message", context.getString(R.string.required_password_length_error));
    } else if (!(hasDigits(password))){
      result.put("message", context.getString(R.string.required_password_with_digit_error));
    } else if (!(hasUppercase(password))){
      result.put("message", context.getString(R.string.required_password_with_uppercase_error));
    } else if (!(hasLowercase(password))){
      result.put("message", context.getString(R.string.required_password_with_lowercase_error));
    } else if(!(hasSymbols(password))){
      result.put("message", context.getString(R.string.required_password_with_special_char_error));
    } else {
      result.put("valid", true);
    }

    return result;
  }

  public static boolean validateConfirmPasswordInput(String password, String confirmPassword) {
    return confirmPassword.equals(password);
  }


  private static boolean hasDigits(String text) {
    return text.matches(".*\\d.*");
  }

  private static boolean hasUppercase(String text) {
    return text.matches("[A-Z]");
  }

  private static boolean hasLowercase(String text) {
    return text.matches("[a-z]");
  }

  private static boolean hasSymbols(String text) {
    return text.matches("[!@#$%^&*()-_+=?><.,;:\"'`~]");
  }
}
