package app.androiddev.wallhaven.ui.latest

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.RowScope.gravity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
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
fun Gallery(
    wp: List<WallpaperDetails>,
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit
) {
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


@Composable
fun ThumbNail(wallpaper: WallpaperDetails, modifier: Modifier = Modifier) {

    CoilImage(
        data = wallpaper.thumbs.small,
        modifier = Modifier.size(150.dp) + modifier,
        loading = @Composable {
            Column(modifier = Modifier.fillMaxSize().gravity(align = Alignment.CenterVertically )) {
                Text("Loading", modifier = Modifier.gravity(align = Alignment.CenterVertically))
            }
        },
    )
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
