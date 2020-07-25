package com.mcwilliams.letscompose.di

import android.content.Context
import com.mcwilliams.letscompose.network.LocationApi
import com.mcwilliams.letscompose.network.WeatherApi
import com.mcwilliams.letscompose.ui.current.CurrentWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module(includes = [NetworkModule::class])
class AppModule {
    @Provides
    @Singleton
    fun providesCurrentWeatherRepository(
        @ApplicationContext context: Context,
        weatherApi: WeatherApi,
        locationApi: LocationApi,
    ): CurrentWeatherRepository =
        CurrentWeatherRepository(context, weatherApi, locationApi)
}