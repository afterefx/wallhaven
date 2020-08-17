package app.androiddev.wallhaven.ui.details

import androidx.compose.foundation.Box
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import app.androiddev.wallhaven.extensions.Color
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class WallPaperDetailsCompose @Inject constructor(private val vmAction: DetailAction) {

    @Composable
    fun WallPaperDetailsContent(
        id: String,
        modifier: Modifier = Modifier
    ) {
        val viewmodel: WallPaperDetailsViewModel = viewModel()
        val viewState: WallpaperDetailsViewState by viewmodel.state.collectAsState()

        vmAction.action(op = MVOperation.GetWallpaper, detailsViewModel = viewmodel, id = id)

        if (viewState.loading) {
            loadingScreen()
        } else {
            val wallpaperDetails = viewState.wallpaperDetails
            wallpaperDetails?.let {
                imageDetail(it)
            }
        }
    }

    @Composable
    fun loadingScreen() {
        Text(
            text = "LOADING DATA",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            fontSize = 40.sp
        )
    }

    @Composable
    fun imageDetail(wallpaperDetails: WallpaperDetails) {
        ScrollableColumn(modifier = Modifier.fillMaxSize()) {
            wallpaperDetails.let { data ->
                Column(modifier = Modifier.fillMaxHeight()) {

                    Spacer(modifier = Modifier.preferredHeight(8.dp))

                    CoilImage(
                        data = data.thumbs.small,
                        modifier = Modifier.aspectRatio(data.ratio.toFloat()).fillMaxSize()
                    )

                    Column(modifier = Modifier.padding(24.dp)) {

                        Spacer(modifier = Modifier.preferredHeight(8.dp))

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
        Box(
            backgroundColor = Color(colorString),
            modifier = Modifier
        ) {
            Text(text = colorString, fontSize = 12.sp)
        }
    }


    @Composable
    private fun Label(text: String, value: (@Composable () -> Unit)? = null) {
        if (value != null) {
            Row(verticalGravity = Alignment.CenterVertically) {
                Text(text = text, style = MaterialTheme.typography.h5)
                value()
            }
        } else {
            Text(text = text, style = MaterialTheme.typography.h5)
        }
    }
}