package app.androiddev.wallhaven.ui.details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animate
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTransformGestures
//import androidx.compose.foundation.gestures.rememberZoomableController
//import androidx.compose.foundation.gestures.zoomable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
//import androidx.compose.ui.gesture.DragObserver
//import androidx.compose.ui.gesture.dragGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.androiddev.wallhaven.extensions.toColor
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import app.androiddev.wallhaven.ui.appcontainer.AppContent
import app.androiddev.wallhaven.ui.ScreenState
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun WallPaperDetailsContent() {
    AppContent { vm, vs ->
        val viewModel: WallPaperDetailsViewModel = viewModel()
        val viewState: WallpaperDetailsViewState by viewModel.state.collectAsState()
        val vmAction = DetailAction()

        vmAction.action(
            op = DetailMVOperation.GetWallpaper(
                id = (vs.currentScreen as ScreenState.Detail).id
            ),
            detailsViewModel = viewModel,
        )

        BackHandler(onBack = { vm.goBack() })

        if (viewState.loading) {
            LoadingScreen()
        } else {
            val wallpaperDetails = viewState.wallpaperDetails
            wallpaperDetails?.let {
                ImageDetail(it)
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Text(
        text = "LOADING DATA",
        modifier = Modifier.fillMaxSize(),
        textAlign = TextAlign.Center,
        fontSize = 40.sp
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageDetail(wallpaperDetails: WallpaperDetails) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp
    var zoom by remember { mutableStateOf(1f) }
    val panMinX by remember { derivedStateOf { ((392f - (392 * zoom)) / 2) } }
    val panMaxX = 0f
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val panned = (offsetX != 0f || offsetY != 0f)
    val zoomedIn = zoom > 1f
    var origImageWidth by remember { mutableStateOf(0f) }
    var origImageHeight by remember { mutableStateOf(0f) }
    var imageDisplayedWidth by remember { mutableStateOf(0f) }
    var imageDisplayedHeight by remember { mutableStateOf(0f) }

    val scope = rememberCoroutineScope()
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .combinedClickable(
            onDoubleClick = {
                var end = 0f
                if (panned || zoomedIn) {
                    end = 1f
                    offsetX = 0f
                    offsetY = 0f
                } else {
                    end = 2f
                }
                scope.launch {
                    animate(zoom, end) { value, _ ->
                        zoom = value
                    }
                }
            },
            onClick = {}
        )
    ) {
        item {
            wallpaperDetails.let { data ->
                Column(modifier = Modifier.fillMaxHeight()) {

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        Modifier
                            .pointerInput(Unit) {
                                detectTransformGestures { _, p, z, _ ->
//                                    if (zoom >= 1f) zoom *= z else zoom = 1f
                                    offsetX += p.x
                                    offsetY += p.y
                                    if (offsetX < panMinX) {
                                        offsetX = panMinX
                                    } else if (offsetX > panMaxX) {
                                        offsetX = panMaxX
                                    }
                                }
                            }
                    ) {
                        var statusText by remember { mutableStateOf("") }
                        var showLoading by remember { mutableStateOf(false) }
                        if (showLoading) {
                            CircularProgressIndicator()
                        }
                        if (statusText.isNotEmpty()) {
                            Text(statusText)
                        }
                        CoilImage(
                            data = data.path,
                            contentDescription = null,
                            modifier = Modifier
                                .clipToBounds()
                                .graphicsLayer(scaleX = zoom, scaleY = zoom)
                                .offset {
                                    IntOffset(
                                        offsetX
                                            .coerceIn(panMinX..0f)
                                            .roundToInt(),
                                        offsetY
                                            .roundToInt()
                                    )
                                },
                            onRequestCompleted = {
                                when (it) {
                                    ImageLoadState.Empty -> {
                                        statusText = "Empty"
                                        showLoading = true
                                    }
                                    ImageLoadState.Loading -> showLoading = true
                                    is ImageLoadState.Success -> {
                                        statusText = ""
                                        showLoading = false
                                        origImageWidth = //it.image?.width?.toFloat() ?: 0f
                                            it.painter.intrinsicSize.width
                                        origImageHeight = //it.image?.height?.toFloat() ?: 0f
                                            it.painter.intrinsicSize.height
                                        imageDisplayedWidth =
                                            (screenWidth / origImageWidth) * origImageWidth
                                        imageDisplayedHeight =
                                            (screenWidth / origImageWidth) * origImageHeight
                                    }
                                    is ImageLoadState.Error -> {
                                        statusText = "Error"
                                        showLoading = true
                                    }
                                }
                            }
                        )
                    }
                    if (true) {
                        Column {
                            TextLabel(label = "screenWidthDp", content = screenWidth.toString())
                            TextLabel(label = "origWidth", content = origImageWidth.toString())
                            TextLabel(label = "origHeight", content = origImageHeight.toString())
                            TextLabel(label = "width", content = imageDisplayedWidth.toString())
                            TextLabel(label = "height", content = imageDisplayedHeight.toString())
                            TextLabel("Scale", zoom.toString())
                            TextLabel("xOffset", offsetX.roundToInt().toString())
                            TextLabel(label = "yOffset", content = offsetY.roundToInt().toString())
                            TextLabel("xOffsetDp", offsetX.roundToInt().dp.toString())
                            TextLabel(
                                label = "yOffsetDp",
                                content = offsetY.roundToInt().dp.toString()
                            )
                            TextLabel(
                                label = "minX",
                                content = (0f - ((imageDisplayedWidth * zoom) / 2)).toString()
                            )
                            TextLabel(
                                label = "minY",
                                content = (0f - ((imageDisplayedHeight * zoom) / 2)).toString()
                            )
                        }
                    }

                    if (zoom <= 1f) {
                        Column(modifier = Modifier.padding(24.dp)) {

                            Spacer(modifier = Modifier.height(8.dp))

                            Label(text = "Category: ") {
                                Text(text = data.category, style = MaterialTheme.typography.body1)
                            }

                            Label(text = "Colors:") {
                                ColorTable(data.colors)
                            }

                            Label("Tags:")
                            Table(items = data.tags.map { it.name }, maxItemsPerRow = 3) {
                                Text(text = it)
                                Spacer(modifier = Modifier.width(4.dp))
                            }


                            Label(text = "Uploader: ") {
                                Text(
                                    text = data.uploader.username,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            Label(text = "Created: ") {
                                Text(text = data.created_at, style = MaterialTheme.typography.body1)
                            }
                            Label(text = "Purity: ") {
                                Text(text = data.purity, style = MaterialTheme.typography.body1)
                            }
                            Label(text = "Ratio: ") {
                                Text(text = data.ratio, style = MaterialTheme.typography.body1)
                            }
                            Label(text = "Resolution: ") {
                                Text(text = data.resolution, style = MaterialTheme.typography.body1)
                            }
                            Label(text = "Views: ") {
                                Text(
                                    text = data.views.toString(),
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            Label(text = "Favorites: ") {
                                Text(
                                    text = data.favorites.toString(),
                                    style = MaterialTheme.typography.body1
                                )
                            }
/*
                        val created_at: String,
                        val dimension_x: Int,
                        val dimension_y: Int,
                        val favorites: Int,
                        val file_size: Int,
                        val file_type: String,
                        val id: String,
                        val path: String,
                        val purity: String,
                        val ratio: String,
                        val resolution: String,
                        val short_url: String,
                        val source: String,
                        val tags: List<Tag>,
                        val thumbs: Thumbs,
                        val uploader: Uploader,
                        val url: String,
                        val views: Int
*/
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun ColorTable(colors: List<String>) {
    Table(items = colors) {
        ColorBox(colorString = it)
    }
}

@Composable
private fun Table(
    items: List<String>,
    maxItemsPerRow: Int = 4,
    reducer: @Composable (String) -> Unit,
) {
    var number = 0
    while (number < items.size) {
        Row {
            var i = 0
            while (i < maxItemsPerRow) {
                if (number + i < items.size)
                    reducer(items[number + i])
                i++
            }
            number += maxItemsPerRow
        }
    }
}

@Composable
private fun ColorBox(colorString: String) {
    val color = colorString.toColor()
    Box(
        modifier = Modifier.background(color)
    ) {
        Text(
            text = colorString,
            fontSize = 12.sp,
            color = if (color.luminance() > 0.5f) Color.Black else Color.White
        )
    }
}

@Composable
private fun TextLabel(label: String, content: String) {
    Label("$label: ") {
        Text(content)
    }
}

@Composable
private fun Label(text: String, value: (@Composable () -> Unit)? = null) {
    if (value != null) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = text, style = MaterialTheme.typography.h5)
            value()
        }
    } else {
        Text(text = text, style = MaterialTheme.typography.h5)
    }
}