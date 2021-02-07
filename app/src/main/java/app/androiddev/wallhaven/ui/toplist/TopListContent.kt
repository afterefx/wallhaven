package app.androiddev.wallhaven.ui.toplist

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import app.androiddev.wallhaven.ui.ScreenState
import app.androiddev.wallhaven.ui.gallery.GalleryOperation
import app.androiddev.wallhaven.ui.gallery.GalleryPage

@Composable
fun TopList(
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit,
    scrollState: ScrollState
) {
    GalleryPage<TopListViewModel>(
        operation = GalleryOperation.GetTopListPage,
        updateScreen,
        updateId,
        scrollState
    )
}

