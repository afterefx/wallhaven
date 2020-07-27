package app.androiddev.wallhaven.model.wallhavendata

data class SearchResults(
    val data: List<WallpaperDetails>,
    val meta: Meta
)