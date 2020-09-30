package app.androiddev.wallhaven.ui.gallery

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.RowScope.gravity
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import app.androiddev.wallhaven.ui.ScreenState
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
inline fun <reified T : GalleryViewModel> GalleryPage(
    operation: GalleryOperation,
    noinline updateScreen: (ScreenState) -> Unit,
    noinline updateId: (String) -> Unit,
    scrollState: ScrollState
) {
    val viewmodel: T = viewModel()
    val state by viewmodel.state.collectAsState()
    val vmAction = GalleryAction()

    if (!state.initialized) {
        vmAction.action(
            operation,
            vm = viewmodel,
            page = 1
        )
    }

    val prev = {
        scrollState.scrollTo(0f)
        val prevPage = state.page - 1
        vmAction.action(
            GalleryOperation.Loading,
            vm = viewmodel,
            page = prevPage
        )
        vmAction.action(
            operation,
            vm = viewmodel,
            page = prevPage
        )
    }
    val next = {
        scrollState.scrollTo(0f)
        val nextPage = state.page + 1
        vmAction.action(
            GalleryOperation.Loading,
            vm = viewmodel,
            page = nextPage
        )
        vmAction.action(
            operation,
            vm = viewmodel,
            page = nextPage
        )
    }

    Column {
        PageButtons(page = state.page, onNext = next, onPrev = prev)
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
fun Gallery(
    gridList: List<List<WallpaperDetails>>,
    updateScreen: (ScreenState) -> Unit,
    updateId: (String) -> Unit
) {
    gridList.forEach { row ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            row.forEach { wallpaper ->
                ThumbNail(
                    wallpaper,
                    Modifier.clickable(onClick = {
                        updateId(wallpaper.id)
                        updateScreen(ScreenState.Detail)
                    })
                )
            }

        }
    }
}

@Composable
fun ThumbNail(wallpaper: WallpaperDetails, modifier: Modifier = Modifier) {
    CoilImage(
        data = wallpaper.thumbs.small,
        modifier = Modifier.size(150.dp) + modifier,
        loading = @Composable {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Loading", modifier = Modifier.gravity(align = Alignment.CenterVertically))
            }
        },
    )
}

@Composable
fun PageButtons(
    page: Int, onNext: () -> Unit, onPrev: () -> Unit, showPrev: () -> Boolean = {
        page > 1
    }
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
            .gravity(align = Alignment.CenterVertically),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (page > 1) {
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

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Loading", fontSize = 40.sp, color = Color.White, textAlign = TextAlign.Center)
    }
}