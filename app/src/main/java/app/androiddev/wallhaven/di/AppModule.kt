package app.androiddev.wallhaven.di

import android.content.Context
import app.androiddev.wallhaven.network.WallHavenApi
import app.androiddev.wallhaven.ui.WallHavenRepository
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
    fun providesWallhavenRepository(
        @ApplicationContext context: Context,
        wallHavenApi: WallHavenApi,
    ): WallHavenRepository =
        WallHavenRepository(context, wallHavenApi)

}