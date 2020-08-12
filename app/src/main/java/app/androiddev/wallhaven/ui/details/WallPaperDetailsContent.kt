package app.androiddev.wallhaven.ui.details

import androidx.compose.*
import androidx.ui.core.Modifier
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.input.ImeAction
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import androidx.ui.viewmodel.viewModel
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
                    CoilImage(
                        data = data.thumbs.small,
                        modifier = Modifier.aspectRatio(data.ratio.toFloat()).fillMaxSize()
                    )

                    Column(modifier = Modifier.padding(24.dp)) {

                        Spacer(modifier = Modifier.preferredHeight(8.dp))

                        Text(
                            text = data.thumbs.small,
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            text = data.short_url,
                            style = MaterialTheme.typography.h2
                        )
                        Text(
                            text = data.url,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(0.dp, 8.dp)
                        )

                    }
                }
            }
        }

    }
}