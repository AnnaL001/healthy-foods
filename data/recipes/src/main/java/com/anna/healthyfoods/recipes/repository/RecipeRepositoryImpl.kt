package com.anna.healthyfoods.recipes.repository

import com.anna.healthyfoods.model.api.RecipeSearch
import com.anna.healthyfoods.network.api.service.RecipeSearchApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
  private val recipeSearchApi: RecipeSearchApi
) : RecipeRepository {
  override suspend fun getRecipes(diet: List<String>, health: List<String>, cuisineType: List<String>,
    mealType: List<String>, dishType: List<String>): Flow<RecipeSearch> {
    return recipeSearchApi.getRecipes(
      diet = diet.toTypedArray(),
      health = health.toTypedArray(),
      cuisineType = cuisineType.toTypedArray(),
      mealType = mealType.toTypedArray(),
      dishType = dishType.toTypedArray()
    )
  }
}