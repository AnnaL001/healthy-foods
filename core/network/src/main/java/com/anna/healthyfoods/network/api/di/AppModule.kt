package com.anna.healthyfoods.network.api.di

import com.anna.healthyfoods.network.api.service.RecipeSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  @Singleton
  @Provides
  fun getRecipeSearchApi(@HealthyFoodsRetrofit retrofit: Retrofit): RecipeSearchApi {
    return retrofit.create(RecipeSearchApi::class.java)
  }
}