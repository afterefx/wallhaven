package app.androiddev.wallhaven.auth.di

import android.content.Context
import app.androiddev.wallhaven.auth.ApiKeyController
import app.androiddev.wallhaven.auth.IApiKeyController
import app.androiddev.wallhaven.auth.ILoginController
import app.androiddev.wallhaven.auth.LoginController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AuthModule {
    @Binds
    abstract fun bindsLoginController(loginController: LoginController): ILoginController

    @Binds
    abstract fun bindsApiKeyController(apiKeyController: ApiKeyController): IApiKeyController

    companion object {
        @Provides
        @Singleton
        fun providesSharedPreferences(@ApplicationContext context: Context) =
            context.getSharedPreferences("wallhaven", Context.MODE_PRIVATE)

    }

}