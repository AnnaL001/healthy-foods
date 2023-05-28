package com.anna.healthyfoods.recipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anna.healthyfoods.model.api.RecipeSearch
import com.anna.healthyfoods.recipes.repository.RecipeRepository
import com.anna.healthyfoods.recipes.ui_state.recipe_list.RecipeListItem
import com.anna.healthyfoods.recipes.ui_state.recipe_list.RecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
private val recipeRepository: RecipeRepository
) : ViewModel() {
  private suspend fun _getRecipes(diet: List<String>, health: List<String>, cuisineType: List<String>,
                                 mealType: List<String>, dishType: List<String>) : Flow<RecipeSearch>{
    return recipeRepository.getRecipes(
      diet = diet,
      health = health,
      cuisineType = cuisineType,
      mealType = mealType,
      dishType = dishType
    )
  }

  suspend fun getRecipes(diet: List<String>, health: List<String>, cuisineType: List<String>,
                         mealType: List<String>, dishType: List<String>): StateFlow<RecipesUiState>{
    val recipes = _getRecipes(diet, health, cuisineType, mealType, dishType)
    val transformedRecipes = _transformRecipeList(recipes)

    return transformedRecipes.map { _recipe ->
      RecipesUiState.Success(_recipe)
    }.stateIn(
      scope = viewModelScope,
      initialValue = RecipesUiState.Loading,
      started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )
  }

  private fun _transformRecipeList(recipes: Flow<RecipeSearch>): Flow<List<RecipeListItem>>{
    return recipes.map { _recipes ->
      _recipes.hits.map { _recipe -> RecipeListItem(_recipe.recipe?.label, _recipe.recipe?.images?.THUMBNAIL?.url) }
    }
  }
}