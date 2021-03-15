package app.androiddev.wallhaven.ui.latest

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import app.androiddev.wallhaven.ui.appcontainer.AppContent
import app.androiddev.wallhaven.ui.gallery.GalleryOperation
import app.androiddev.wallhaven.ui.gallery.GalleryPage

@Composable
fun LatestContent() {
    AppContent { _, appViewState ->
        GalleryPage<LatestViewModel>(
            operation = GalleryOperation.GetLatestPage,
            lazyListState =
            appViewState.latestListState
                ?: rememberLazyListState(),
        )
    }
}
