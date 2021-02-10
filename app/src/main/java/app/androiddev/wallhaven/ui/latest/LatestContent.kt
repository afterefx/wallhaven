package app.androiddev.wallhaven.ui.latest

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import app.androiddev.wallhaven.ui.ScreenState
import app.androiddev.wallhaven.ui.gallery.GalleryOperation
import app.androiddev.wallhaven.ui.gallery.GalleryPage

@Composable
fun LatestContent(
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit,
    listState: LazyListState,
) {
    GalleryPage<LatestViewModel>(
        operation = GalleryOperation.GetLatestPage,
        updateScreen,
        updateId,
        listState
    )
}
