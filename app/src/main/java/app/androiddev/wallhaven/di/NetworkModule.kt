package app.androiddev.wallhaven.di

import app.androiddev.wallhaven.network.WallHavenApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


/**
 * Module which provides all required dependencies about network
 */
@InstallIn(ApplicationComponent::class)
@Module
@Suppress("unused")
object NetworkModule {

    private val MY_API_KEY = "zsUcLWkJn6iVLWLNh6XWVGfaM3paWJ2C"

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideWallHavenApi(retrofit: Retrofit): WallHavenApi {
        return retrofit.create(WallHavenApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideWallHavenClientApi(
        okHttpClient: OkHttpClient.Builder
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://wallhaven.cc/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }


    @Provides
    @Reusable
    @JvmStatic
    internal fun provideOkHttp(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)
        okHttpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.url(original.url.toString() + "?apikey=$MY_API_KEY")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return okHttpClient
    }
}