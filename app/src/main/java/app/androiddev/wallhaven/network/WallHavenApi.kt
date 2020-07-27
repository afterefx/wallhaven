package app.androiddev.wallhaven.network

import app.androiddev.wallhaven.model.wallhavendata.*
import app.androiddev.wallhaven.model.wallhavendata.Collection
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WallHavenApi {
    @GET("w/{id}")
    suspend fun getWallpaperDetails(@Path("id") id: String): Data<WallpaperDetails>

    @GET("tag/{id}")
    suspend fun getTag(@Path("id") id: String): Data<Tag>

    @GET("settings")
    suspend fun getSettings(): Data<Settings>

    @GET("collections")
    suspend fun getCollections(): Data<Array<Collection>>

    @GET("search")
    suspend fun getSearch(
        @Query("q") q: String,
        @Query("categories") categories: String = "111",
        @Query("purity") purity: String = "100",
        @Query("sorting") sorting: String = "date_added",
        @Query("order") order: String = "desc",
        @Query("topRange") topRange: String = "1M",
        @Query("atleast") atleast: String,
        @Query("resolutions") resolutions: String,
        @Query("ratios") ratios: String,
        @Query("colors") colors: String,
        @Query("page") page: String,
        @Query("seed") seed: String
    ): SearchResults
}
