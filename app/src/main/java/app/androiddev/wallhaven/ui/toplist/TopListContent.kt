package app.androiddev.wallhaven.ui.toplist

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import app.androiddev.wallhaven.ui.ScreenState
import app.androiddev.wallhaven.ui.gallery.GalleryOperation
import app.androiddev.wallhaven.ui.gallery.GalleryPage

@Composable
fun TopList(
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit,
    listState: LazyListState,
) {
    GalleryPage<TopListViewModel>(
        operation = GalleryOperation.GetTopListPage,
        updateScreen,
        updateId,
        listState
    )
}

