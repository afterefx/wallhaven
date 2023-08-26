package app.androiddev.wallhaven.ui.latest

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import app.androiddev.wallhaven.ui.appcontainer.AppContent
import app.androiddev.wallhaven.ui.gallery.GalleryOperation
import app.androiddev.wallhaven.ui.gallery.GalleryPage

@Composable
fun LatestContent(paddingValues: PaddingValues, navController: NavHostController) {
    AppContent { _, appViewState ->
        GalleryPage<LatestViewModel>(
            navController,
            operation = GalleryOperation.GetLatestPage,
            lazyListState = appViewState.latestListState ?: rememberLazyGridState(),
        )
    }
}
