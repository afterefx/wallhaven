package app.androiddev.wallhaven.ui.latest

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import app.androiddev.wallhaven.ui.appcontainer.AppContent
import app.androiddev.wallhaven.ui.gallery.GalleryOperation
import app.androiddev.wallhaven.ui.gallery.GalleryPage

@Composable
fun LatestContent(navController: NavHostController) {
    AppContent { _, appViewState ->
        GalleryPage<LatestViewModel>(
            navController,
            operation = GalleryOperation.GetLatestPage,
            lazyListState = appViewState.latestListState ?: rememberLazyGridState(),
        )
    }
}
