package app.androiddev.wallhaven.ui

import android.content.Context
import android.content.SharedPreferences
import app.androiddev.wallhaven.R
import app.androiddev.wallhaven.model.wallhavendata.*
import app.androiddev.wallhaven.model.wallhavendata.Collection
import app.androiddev.wallhaven.network.WallHavenApi
import javax.inject.Inject

class WallHavenRepository @Inject constructor(
    context: Context,
    private val wallHavenApi: WallHavenApi
) {

    private val preferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    suspend fun getWallPaperDetails(id: String): WallpaperDetails {
        return wallHavenApi.getWallpaperDetails(id).data
    }

    suspend fun getTag(id: String): Tag {
        return wallHavenApi.getTag(id).data
    }

    suspend fun getSettings(): Settings {
        return wallHavenApi.getSettings().data
    }

    suspend fun getCollections(): Array<Collection> {
        return wallHavenApi.getCollections().data
    }

    suspend fun getLatest(): SearchResults {
        return search()
    }

    suspend fun search(query: SearchQuery = SearchQuery()): SearchResults {
        return wallHavenApi.getSearch(
            query.query,
            query.categories.string,
            query.purity.string,
            query.sorting.string,
            query.order.string,
            query.topRange.string,
            query.atleast,
            query.resolutions,
            query.ratios,
            query.colors.string,
            query.page.toString(),
            query.seed
        )
//        val locations = preferences.getStringSet("locations", mutableSetOf())
//        locations!!.add(search)
//        preferences.edit().putStringSet("locations", locations).apply()
    }

    fun String.intOrString() = toIntOrNull() ?: this
}