package app.androiddev.wallhaven.model.wallhavendata

data class WallpaperDetails(
    val category: String,
    val colors: List<String>,
    val created_at: String,
    val dimension_x: Int,
    val dimension_y: Int,
    val favorites: Int,
    val file_size: Int,
    val file_type: String,
    val id: String,
    val path: String,
    val purity: String,
    val ratio: String,
    val resolution: String,
    val short_url: String,
    val source: String,
    val tags: List<Tag>,
    val thumbs: Thumbs,
    val uploader: Uploader,
    val url: String,
    val views: Int
)