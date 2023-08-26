package app.androiddev.wallhaven.ui.details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animate
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.androiddev.wallhaven.extensions.toColor
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import app.androiddev.wallhaven.ui.Screen
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun WallPaperDetailsContent(navController: NavController, id: String) {
    if (id.isEmpty()) navController.navigate(Screen.Latest.route)
    val viewModel: WallPaperDetailsViewModel = hiltViewModel()
    val viewState: WallpaperDetailsViewState by viewModel.state.collectAsState()
    val vmAction = DetailAction()

    vmAction.action(
        op = DetailMVOperation.GetWallpaper(id = id),
        detailsViewModel = viewModel,
    )

    BackHandler(onBack = { navController.popBackStack() })

    if (viewState.loading) {
        LoadingScreen()
    } else {
        val wallpaperDetails = viewState.wallpaperDetails
        wallpaperDetails?.let {
            ImageDetail(it)
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
    val screenHeight = config.screenHeightDp
    var zoom by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val panned = (offsetX != 0f || offsetY != 0f)
    val zoomedIn = zoom > 1f
    var origImageWidth by remember { mutableStateOf(0f) }
    var origImageHeight by remember { mutableStateOf(0f) }
    var imageDisplayedWidth by remember { mutableStateOf(0f) }
    var imageDisplayedHeight by remember { mutableStateOf(0f) }
    val panMinX by remember { derivedStateOf { ((imageDisplayedWidth - (imageDisplayedWidth * zoom)) / 2) } }
    val panMaxX by remember { derivedStateOf { ((imageDisplayedWidth + (imageDisplayedWidth * zoom)) / 2 / 2) } }
    val panMinY by remember { derivedStateOf { if (zoomedIn) ((imageDisplayedHeight - (imageDisplayedWidth * zoom)) / 2) else 0f } }
    val panMaxY by remember { derivedStateOf { ((imageDisplayedHeight + (imageDisplayedWidth * zoom)) / 2 / 2) } }

    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .zIndex(1f)
            .fillMaxSize()
            .combinedClickable(
                onDoubleClick = {
                    val end: Float
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
            .pointerInput(Unit) {
                detectTransformGestures { _, p, z, _ ->
                    if (zoom >= 1f) {
                        zoom *= z
                    } else {
                        zoom = 1f
                    }
                    offsetX += p.x
                    offsetY += p.y
                    if (offsetX < panMinX) {
                        offsetX = panMinX
                    } else if (offsetX > panMaxX) {
                        offsetX = panMaxX
                    }

                    if (offsetY < panMinY) {
                        offsetY = panMinY
                    } else if (offsetY > panMaxY) {
                        offsetY = panMaxY
                    }
                }
            }
    )

    wallpaperDetails.let { data ->
        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(8.dp))

            val imageDisplayedModifier = if (zoomedIn) Modifier else Modifier.clipToBounds()
            Box(modifier = imageDisplayedModifier.fillMaxWidth()) {
                var statusText by remember { mutableStateOf("") }
                var showLoading by remember { mutableStateOf(false) }

                val imagePainter = rememberImagePainter(data.path)
                when (imagePainter.state) {
                    is ImagePainter.State.Success -> {
                        // Perform the transition animation.
                        statusText = ""
                        showLoading = false
                        origImageWidth = //it.image?.width?.toFloat() ?: 0f
                            imagePainter.intrinsicSize.width
                        origImageHeight = //it.image?.height?.toFloat() ?: 0f
                            imagePainter.intrinsicSize.height
                        imageDisplayedWidth =
                            (screenWidth / origImageWidth) * origImageWidth
                        imageDisplayedHeight =
                            (screenWidth / origImageWidth) * origImageHeight
                    }
                    ImagePainter.State.Empty -> {
                        statusText = "Empty"
                        showLoading = true
                    }
                    is ImagePainter.State.Loading -> {
                        showLoading = true
                    }
                    is ImagePainter.State.Error -> {
                        statusText = "Error"
                        showLoading = true
                    }
                }

                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .graphicsLayer(scaleX = zoom, scaleY = zoom)
                        .offset {
                            IntOffset(
                                offsetX
                                    .coerceIn(panMinX..panMaxX)
                                    .roundToInt(),
                                offsetY
                                    .coerceIn(panMinY..panMaxY)
                                    .roundToInt()
                            )
                        }
                )
                if (showLoading) CircularProgressIndicator()
                if (statusText.isNotEmpty()) Text(statusText)
            }
            if (false) {
                Column {
                    TextLabel(label = "screenWidthDp", content = screenWidth.toString())
                    TextLabel(label = "screenHeightDp", content = screenHeight.toString())
                    TextLabel(label = "origWidth", content = origImageWidth.toString())
                    TextLabel(label = "origHeight", content = origImageHeight.toString())
                    TextLabel(label = "width", content = imageDisplayedWidth.toString())
                    TextLabel(label = "height", content = imageDisplayedHeight.toString())
                    TextLabel(label = "panMinX", content = panMinX.toString())
                    TextLabel(label = "panMaxX", content = panMaxX.toString())

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

            if (!zoomedIn) ImageInfo(data)
        }
    }

}

@Composable
fun ImageInfo(data: WallpaperDetails) {

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
        modifier = Modifier
            .background(color)
            .padding(1.dp)
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