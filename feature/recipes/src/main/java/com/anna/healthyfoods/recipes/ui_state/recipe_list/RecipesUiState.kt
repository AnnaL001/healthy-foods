package com.anna.healthyfoods.recipes.ui_state.recipe_list

sealed interface RecipesUiState{
  object Loading: RecipesUiState
  data class Success(val recipes: List<RecipeListItem>) : RecipesUiState
}