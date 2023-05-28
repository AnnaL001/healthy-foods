package com.anna.healthyfoods.recipes.repository

import com.anna.healthyfoods.model.api.RecipeSearch
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
  suspend fun getRecipes(
    diet: List<String>,
    health: List<String>,
    cuisineType: List<String>,
    mealType: List<String>,
    dishType: List<String>
  ): Flow<RecipeSearch>
}