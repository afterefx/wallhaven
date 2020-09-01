package app.androiddev.wallhaven.ui.random

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.RowScope.gravity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import app.androiddev.wallhaven.ui.ScreenState
import app.androiddev.wallhaven.ui.gallery.*

@Composable
fun RandomContent(
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit
) {
    val operation = GalleryOperation.GetRandomListPage

    val viewmodel: RandomViewModel = viewModel()
    val state by viewmodel.state.collectAsState()
    val vmAction = GalleryAction()
    val scrollState = rememberScrollState()

    if (!state.initialized) {
        vmAction.action(op = operation, vm = viewmodel)
    }

    Column {
        RefreshButton {
            scrollState.scrollTo(0f)
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
            ScrollableColumn(scrollState = scrollState) {
                state.list?.let { list ->
                    Gallery(gridList = list, updateScreen = updateScreen, updateId = updateId)
                }
                Spacer(modifier = Modifier.height(120.dp))
            }
        }
    }
}

@Composable
fun RefreshButton(onclick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
            .gravity(align = Alignment.CenterVertically),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onclick) {
            Text("Refresh")
        }
    }
}

