package app.androiddev.wallhaven.ui.latest

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import app.androiddev.wallhaven.ui.ScreenState
import app.androiddev.wallhaven.ui.gallery.GalleryOperation
import app.androiddev.wallhaven.ui.gallery.GalleryPage

@Composable
fun LatestContent(
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit,
    scrollState: ScrollState
) {
    GalleryPage<LatestViewModel>(operation = GalleryOperation.GetLatestPage, updateScreen, updateId, scrollState)
}
