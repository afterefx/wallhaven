package app.androiddev.wallhaven.ui.random

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import app.androiddev.wallhaven.ui.ScreenState
import app.androiddev.wallhaven.ui.gallery.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalFoundationApi::class)
@Composable
fun RandomContent(
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit,
    lazyListState: LazyListState,
) {
    val operation = GalleryOperation.GetRandomListPage

    val viewmodel: RandomViewModel = viewModel()
    val state by viewmodel.state.collectAsState()
    val vmAction = GalleryAction()

    if (!state.initialized) {
        vmAction.action(op = operation, vm = viewmodel)
    }

    val scope = rememberCoroutineScope()
    Column {
        RefreshButton {
            scope.launch {
                lazyListState.snapToItemIndex(0)
            }
            vmAction.action(
                GalleryOperation.Loading,
                viewmodel
            )
            vmAction.action(
                operation,
                viewmodel
            )
        }
        if (state.loading) {
            LoadingScreen()
        } else {
            LazyVerticalGrid(cells = GridCells.Fixed(2), state = lazyListState) {
                items(state.list ?: emptyList()) { wallpaper ->
                    ThumbNail(
                        wallpaper,
                        Modifier.clickable(onClick = {
                            updateId(wallpaper.id)
                            updateScreen(ScreenState.Detail)
                        })
                    )
                }
                item { Spacer(modifier = Modifier.height(120.dp)) }
            }
        }
    }
}

@Composable
fun RefreshButton(onclick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = onclick) {
            Text("Refresh")
        }
    }
}

