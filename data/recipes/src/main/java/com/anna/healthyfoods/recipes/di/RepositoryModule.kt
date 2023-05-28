package com.anna.healthyfoods.recipes.di

import com.anna.healthyfoods.recipes.repository.RecipeRepository
import com.anna.healthyfoods.recipes.repository.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
  @ViewModelScoped
  @Binds
  abstract fun bindRecipeRepository(
    recipeRepositoryImpl: RecipeRepositoryImpl
  ): RecipeRepository
}