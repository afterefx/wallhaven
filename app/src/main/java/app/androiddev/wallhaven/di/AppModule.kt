package app.androiddev.wallhaven.di

import android.content.Context
import app.androiddev.wallhaven.network.WallHavenApi
import app.androiddev.wallhaven.ui.WallHavenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
abstract class AppModule {
    companion object {
        @Provides
        @Singleton
        fun providesWallhavenRepository(
            @ApplicationContext context: Context,
            wallHavenApi: WallHavenApi,
        ): WallHavenRepository =
            WallHavenRepository(context, wallHavenApi)

    }

}