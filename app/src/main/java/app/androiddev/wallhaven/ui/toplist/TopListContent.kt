package app.androiddev.wallhaven.ui.toplist

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import app.androiddev.wallhaven.ui.appcontainer.AppContent
import app.androiddev.wallhaven.ui.gallery.GalleryOperation
import app.androiddev.wallhaven.ui.gallery.GalleryPage

@Composable
fun TopList(navController: NavController) {
    AppContent { _, vs ->
        GalleryPage<TopListViewModel>(
            navController,
            operation = GalleryOperation.GetTopListPage,
            lazyListState = vs.topListState ?: rememberLazyListState(),
        )
    }
}

