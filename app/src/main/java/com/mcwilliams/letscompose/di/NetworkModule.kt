package com.mcwilliams.letscompose.di

import com.mcwilliams.letscompose.network.LocationApi
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
    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideLocationApi(@Named("geolocation") retrofit: Retrofit): LocationApi {
        return retrofit.create(LocationApi::class.java)
    }

    @Provides
    @Named("geolocation")
    @Reusable
    @JvmStatic
    internal fun provideGeoLocationApi(
        okHttpClient: OkHttpClient.Builder
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://api.opencagedata.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

//
//    /**
//     * Provides the Retrofit object.
//     * @return the Retrofit object
//     */
//    @Provides
//    @Named("sheety")
//    @Reusable
//    @JvmStatic
//    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient.Builder): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("https://v2-api.sheety.co/3914cbe7336242c6f95cabe092d3ae4e/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .client(okHttpClient.build())
//            .build()
//    }


    @Provides
    @Reusable
    @JvmStatic
    internal fun provideOkHttp(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)
        return okHttpClient
    }
}