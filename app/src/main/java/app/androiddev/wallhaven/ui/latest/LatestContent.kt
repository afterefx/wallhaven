package app.androiddev.wallhaven.ui.latest

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.ui.core.Modifier
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.unit.dp
import androidx.ui.viewmodel.viewModel
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import app.androiddev.wallhaven.ui.ScreenState
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun LatestContent(
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit
) {
    val viewmodel: LatestWallPapersViewModel = viewModel()
    val wallpaperSearchResults by viewmodel.latestWallpapers.observeAsState()

    ScrollableColumn {
        wallpaperSearchResults?.data?.let { list ->
            Gallery(wp = list, updateScreen = updateScreen, updateId = updateId)
        }
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun ThumbNail(wallpaper: WallpaperDetails, modifier: Modifier = Modifier) {
    CoilImage(
        data = wallpaper.thumbs.small,
        modifier = Modifier.size(150.dp) + modifier,
        loading = @Composable { Text("loading") },
    )
}

@Composable
fun Gallery(wp: List<WallpaperDetails>, updateScreen: (ScreenState) -> Unit, updateId: (String) -> Unit) {
    val gridList = gridWallPapers(wp)

    gridList.forEach { row ->

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            row.forEach { wallpaper ->
                ThumbNail(
                    wallpaper,
                    Modifier.clickable(onClick = {
                        updateId(wallpaper.id)
                        updateScreen(ScreenState.Detail)
                    })
                )
            }

        }
    }
}

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
