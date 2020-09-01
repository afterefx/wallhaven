package app.androiddev.wallhaven.ui.random

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.RowScope.gravity
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    if (state.loading) {
        vmAction.action(op = operation, vm = viewmodel)
        LoadingScreen()
    } else {
        ScrollableColumn {
            state.list?.let { list ->
                RefreshButton(vmAction, viewmodel, operation)
                Gallery(gridList = list, updateScreen = updateScreen, updateId = updateId)
            }
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@Composable
fun RefreshButton(
    vmAction: GalleryAction,
    viewmodel: RandomViewModel,
    operation: GalleryOperation.GetRandomListPage
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
            .gravity(align = Alignment.CenterVertically),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {
            vmAction.action(
                operation,
                viewmodel,
            )
        }) {
            Text("Refresh")
        }
    }
}

