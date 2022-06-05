package com.anna.healthyfoods.interfaces;

import com.anna.healthyfoods.models.RecipeSearchResponse;
import com.anna.healthyfoods.utility.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EdamamApi {
  @GET("v2")
  Call<RecipeSearchResponse> getRecipesByMealType(
          @Query(Constants.API_ID_QUERY_PARAMETER) String appId,
          @Query(Constants.MEAL_TYPE_QUERY_PARAMETER) String mealType);
}
