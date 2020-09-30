package app.androiddev.wallhaven.ui.toplist

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import app.androiddev.wallhaven.ui.ScreenState
import app.androiddev.wallhaven.ui.gallery.*

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

