package app.androiddev.wallhaven.util

import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails

fun gridWallPapers(wallpapersList: List<WallpaperDetails>): List<List<WallpaperDetails>> {
    val gridList: MutableList<List<WallpaperDetails>> = mutableListOf()

    var innerList: MutableList<WallpaperDetails> = mutableListOf()
    wallpapersList.forEachIndexed { index, wallpaperDetails ->
        if (index % 2 != 0 || index == 0) {
            innerList = mutableListOf()
            innerList.add(wallpaperDetails)
        } else {
            innerList.add(wallpaperDetails)
            gridList.add(innerList)
        }
    }

    return gridList
}
