package app.androiddev.wallhaven.ui.toplist

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import app.androiddev.wallhaven.ui.Gallery
import app.androiddev.wallhaven.ui.LoadingScreen
import app.androiddev.wallhaven.ui.PageButtons
import app.androiddev.wallhaven.ui.ScreenState
import app.androiddev.wallhaven.ui.latest.LatestContentAction
import app.androiddev.wallhaven.ui.latest.MVOperation

@Composable
fun TopList(
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit
) {
    val viewmodel: TopListViewModel = viewModel()
    val state by viewmodel.state.collectAsState()
    val vmAction = TopListAction()

    if (state.loading) {
        vmAction.action(op = TopListOperation.GetGalleryPage, viewModel = viewmodel, page = 1)
        LoadingScreen()
    } else {
        ScrollableColumn {
            val prev = {
                vmAction.action(
                    TopListOperation.GetGalleryPage,
                    viewModel = viewmodel,
                    page = (state.page - 1)
                )
            }
            val next = {
                vmAction.action(
                    TopListOperation.GetGalleryPage,
                    viewModel = viewmodel,
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

