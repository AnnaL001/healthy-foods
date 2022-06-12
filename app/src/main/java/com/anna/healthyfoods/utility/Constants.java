package com.anna.healthyfoods.utility;

import com.anna.healthyfoods.BuildConfig;

public class Constants {
  public static final String EDAMAM_API_ID = BuildConfig.EDAMAM_API_ID;
  public static final String EDAMAM_API_KEY = BuildConfig.EDAMAM_API_KEY;
  public static final String EDAMAM_API_BASE_URL = "https://api.edamam.com/api/recipes/";
  public static final String APP_ID_QUERY_PARAMETER = "app_id";
  public static final String API_KEY_QUERY_PARAMETER = "app_key";
  public static final String MEAL_TYPE_QUERY_PARAMETER = "mealType";
  public static final String DIET_LABEL_QUERY_PARAMETER = "diet";
  public static final String HEALTH_LABEL_QUERY_PARAMETER = "health";
  public static final String SEARCH_QUERY_PARAMETER = "q";
  public static final String SEARCH_TYPE_QUERY_PARAMETER = "type";
  public static final String FIREBASE_CHILD_SETTINGS_LOCATION = "settings";
  public static final String FIREBASE_CHILD_RECIPE_LOCATION = "recipes";
}
