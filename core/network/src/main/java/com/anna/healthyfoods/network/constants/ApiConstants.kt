package com.anna.healthyfoods.network.constants

import com.anna.healthyfoods.network.BuildConfig
import java.lang.StringBuilder

object ApiConstants {
  private const val SEARCH_QUERY_TYPE_PARAMETER = "type"
  private const val SEARCH_QUERY_TYPE_VALUE = "public"
  private const val SEARCH_QUERY_APP_ID_PARAMETER = "app_id"
  private const val SEARCH_QUERY_API_KEY_PARAMETER = "app_key"

  const val SEARCH_QUERY_PARAMETER = "q"
  const val SEARCH_QUERY_DISH_TYPE_PARAMETER = "dishType"
  const val SEARCH_QUERY_MEAL_TYPE_PARAMETER = "mealType"
  const val SEARCH_QUERY_CUISINE_TYPE_PARAMETER = "cuisineType"
  const val SEARCH_QUERY_DIET_PARAMETER = "diet"
  const val SEARCH_QUERY_HEALTH_PARAMETER = "health"

  val BASE_URL = StringBuilder("https://api.edamam.com/api/recipes/v2?")
    .append("$SEARCH_QUERY_TYPE_PARAMETER=$SEARCH_QUERY_TYPE_VALUE&") //append recipe type
    .append("$SEARCH_QUERY_APP_ID_PARAMETER=${BuildConfig.APP_ID}&") //append app_id
    .append("$SEARCH_QUERY_API_KEY_PARAMETER=${BuildConfig.API_KEY}") //append api_key
    .toString()
}