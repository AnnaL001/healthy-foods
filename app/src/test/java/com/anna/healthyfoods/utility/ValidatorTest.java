package com.anna.healthyfoods.utility;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidatorTest {
  String name;
  List<String> selectedDiets;
  List<String> selectedAllergies;

  @Before
  public void setUp() {
    name = "Anna";
    selectedDiets = new ArrayList<>(Arrays.asList("Keto", "Low carb"));
    selectedAllergies = new ArrayList<>(Arrays.asList("Glutten", "Wheat", "Shellfish"));
  }

  @Test
  public void validateRequiredFormInput_returnsTrueIfAllInputsAreProvided_true() {
    assertTrue(Validator.validateRequiredUserDetailsFormInput(name, selectedDiets, selectedAllergies));
  }

  @Test
  public void validateCorrectNameInput_returnsTrueIfNameHasMoreThanThreeCharacters_true() {
    assertTrue(Validator.validateCorrectNameInput(name));
  }

  @Test
  public void validateRequiredFormInput_returnsFalseIfNameInputIsNotProvided_false() {
    String emptyName = "";
    assertFalse(Validator.validateRequiredUserDetailsFormInput(emptyName, selectedDiets, selectedAllergies));
  }

  @Test
  public void validateRequiredFormInput_returnsFalseIfSelectedDietsInputIsNotProvided_false() {
    List<String> emptySelectedDietsList = new ArrayList<>();
    assertFalse(Validator.validateRequiredUserDetailsFormInput(name, emptySelectedDietsList, selectedAllergies));
  }

  @Test
  public void validateRequiredFormInput_returnsFalseIfSelectedAllergiesInputIsNotProvided_false() {
    List<String> emptySelectedAllergiesList = new ArrayList<>();
    assertFalse(Validator.validateRequiredUserDetailsFormInput(name, selectedDiets, emptySelectedAllergiesList));
  }

  @Test
  public void validateCorrectNameInput_returnsFalseIfNameHasLessThanFourCharacters() {
    String incorrectNameInput = "L";
    assertFalse(Validator.validateCorrectNameInput(incorrectNameInput));
  }

  @Test
  public void validateCorrectNameInput_returnsFalseIfNameHasDigits() {
    String incorrectNameInput = "L123";
    assertFalse(Validator.validateCorrectNameInput(incorrectNameInput));
  }
}