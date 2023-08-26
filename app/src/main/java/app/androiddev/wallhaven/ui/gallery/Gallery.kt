package app.androiddev.wallhaven.ui.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

@Composable
inline fun <reified T : GalleryViewModel> GalleryPage(
    navController: NavController,
    operation: GalleryOperation,
    lazyListState: LazyGridState = rememberLazyGridState(),
) {
    val viewModel: T = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val vmAction = GalleryAction()

    if (!state.initialized) {
        vmAction.action(
            operation,
            vm = viewModel,
            page = 1
        )
    }

    val scope = rememberCoroutineScope()
    val prev = {
        scope.launch {
            lazyListState.scrollToItem(0)
        }
        val prevPage = state.page - 1
        vmAction.action(
            GalleryOperation.Loading,
            vm = viewModel,
            page = prevPage
        )
        vmAction.action(
            operation,
            vm = viewModel,
            page = prevPage
        )
    }
    val next = {
        scope.launch {
            lazyListState.scrollToItem(0)
        }
        val nextPage = state.page + 1
        vmAction.action(
            GalleryOperation.Loading,
            vm = viewModel,
            page = nextPage
        )
        vmAction.action(
            operation,
            vm = viewModel,
            page = nextPage
        )
    }

    Column {
        PageButtons(page = state.page, onNext = next, onPrev = prev)
        if (state.loading) {
            LoadingScreen()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = lazyListState,
                contentPadding = PaddingValues(bottom = 100.dp),
            ) {
                items(items = state.list ?: emptyList()) { wallpaper ->
                    Thumbnail(
                        wallpaper,
                        Modifier.clickable(onClick = {
                            navController.navigate("detail/${wallpaper.id}")
                        })
                    )
                }
            }
        }
    }
}

@Composable
fun Thumbnail(wallpaper: WallpaperDetails, modifier: Modifier = Modifier) {
    val painter = rememberImagePainter(data = wallpaper.thumbs.small)
    if (painter.state is ImagePainter.State.Loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Loading", modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    } else {
        Image(
            painter = painter,
            contentDescription = wallpaper.category,
            modifier = Modifier
                .size(150.dp)
                .then(modifier),
        )
    }
}

@Composable
fun PageButtons(
    page: Int,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    showPrev: () -> Boolean = { page > 1 }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showPrev()) {
            Button(onClick = onPrev) {
                Text(text = "Prev")
            }
        } else {
            Spacer(modifier = Modifier.width(60.dp))
        }

        Text(
            text = page.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Button(onClick = onNext) {
            Text(text = "Next")
        }

    }
}

@Preview
@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Loading", fontSize = 40.sp, color = Color.White, textAlign = TextAlign.Center)
    }
}
