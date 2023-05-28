package com.anna.healthyfoods.network.api.di

import com.anna.healthyfoods.network.api.NetworkInterceptor
import com.anna.healthyfoods.network.constants.ApiConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Singleton
  @HealthyFoodsOkHttpClient
  @Provides
  fun provideOkHttpClient(): OkHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(NetworkInterceptor())
    .build()

  @Singleton
  @HealthyFoodsRetrofit
  @Provides
  fun getRetrofit(@HealthyFoodsOkHttpClient okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }
}