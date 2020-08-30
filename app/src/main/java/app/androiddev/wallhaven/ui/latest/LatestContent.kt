package app.androiddev.wallhaven.ui.latest

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope.gravity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import app.androiddev.wallhaven.ui.Gallery
import app.androiddev.wallhaven.ui.LoadingScreen
import app.androiddev.wallhaven.ui.PageButtons
import app.androiddev.wallhaven.ui.ScreenState

@Composable
fun LatestContent(
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit
) {
    val viewmodel: LatestWallPapersViewModel = viewModel()
    val state by viewmodel.state.collectAsState()
    val vmAction = LatestContentAction()

    if (state.loading) {
        vmAction.action(op = MVOperation.GetGalleryPage, latestViewModel = viewmodel, page = 1)
        LoadingScreen()
    } else {
        ScrollableColumn {
            val prev = {
                vmAction.action(
                    MVOperation.GetGalleryPage,
                    latestViewModel = viewmodel,
                    page = (state.page - 1)
                )
            }
            val next = {
                vmAction.action(
                    MVOperation.GetGalleryPage,
                    latestViewModel = viewmodel,
                    page = (state.page + 1)
                )
            }
            state.list?.let { list ->
                PageButtons(page = state.page, onNext = next, onPrev = prev)
                Gallery(gridList = list, updateScreen = updateScreen, updateId = updateId)
                PageButtons(page = state.page, onNext = next, onPrev = prev)
            }
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}


