package app.androiddev.wallhaven.model.wallhavendata

data class Collection(
    val count: Int,
    val id: Int,
    val label: String,
    val `public`: Int,
    val views: Int
)