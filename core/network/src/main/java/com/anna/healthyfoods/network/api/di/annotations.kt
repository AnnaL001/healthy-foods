package com.anna.healthyfoods.network.api.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HealthyFoodsOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HealthyFoodsRetrofit