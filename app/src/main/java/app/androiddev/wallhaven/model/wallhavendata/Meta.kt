package app.androiddev.wallhaven.model.wallhavendata

data class Meta(
    val current_page: Int,
    val last_page: Int,
    val per_page: Int,
    val query: String?,
    val seed: String?,
    val total: Int
)