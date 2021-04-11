package app.androiddev.wallhaven.ui.random

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.androiddev.wallhaven.ui.appcontainer.AppContent
import app.androiddev.wallhaven.ui.gallery.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalFoundationApi::class)
@Composable
fun RandomContent(navController: NavController) {
    AppContent { vm, vs ->
        val lazyListState = vs.randomListState ?: rememberLazyListState()
        val operation = GalleryOperation.GetRandomListPage

        val viewModel: RandomViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        val vmAction = GalleryAction()

        if (!state.initialized) {
            vmAction.action(op = operation, vm = viewModel)
        }

        val scope = rememberCoroutineScope()
        Column {
            RefreshButton {
                scope.launch {
                    lazyListState.scrollToItem(0)
                }
                vmAction.action(
                    GalleryOperation.Loading,
                    viewModel
                )
                vmAction.action(
                    operation,
                    viewModel
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
                                navController.navigate("detail/${wallpaper.id}")
                            })
                        )
                    }
                    item { Spacer(modifier = Modifier.height(120.dp)) }
                }
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

