package app.androiddev.wallhaven.ui.current

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.ui.core.Modifier
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.unit.dp
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import app.androiddev.wallhaven.ui.MainActivityScreen
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun LatestContent(
    currentScreen: MainActivityScreen,
    viewmodel: LatestWallPapersViewModel
) {
    val wallpaperSearchResults by viewmodel.latestWallpapers.observeAsState()

    ScrollableColumn {
        wallpaperSearchResults?.data?.let { list ->
            val gridList = viewmodel.gridWallPapers(list)
            gridList.forEach { row ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { wallpaper ->
                        ThumbNail(wallpaper, Modifier.clickable(onClick = { TODO(reason = "make it clickable and go to detail page")} ))
                    }

                }
            }
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