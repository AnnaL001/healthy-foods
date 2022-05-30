package com.anna.healthyfoods.utility;

import java.util.List;

public class Validator {
  // Returns false if form input values are empty and name is less than four characters
  public static boolean validateRequiredUserDetailsFormInput(String name, List<String> selectedDiets, List<String> selectedAllergies){
    return !name.isEmpty() && selectedDiets.size() != 0 && selectedAllergies.size() != 0;
  }

  // Returns false if name is less than four characters
  public static boolean validateCorrectNameInput(String name){
    return !(name.length() < 4 || name.matches(".*\\d.*"));
  }
}
