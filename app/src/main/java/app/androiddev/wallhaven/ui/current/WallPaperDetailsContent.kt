package app.androiddev.wallhaven.ui.current

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.unit.dp
import app.androiddev.wallhaven.ui.MainActivityScreen
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun WallPaperDetailsContent(
    currentScreen: MainActivityScreen,
    modifier: Modifier,
    viewmodel: WallPaperDetailsViewModel
) {
    val wallpaperDetails by viewmodel.wallpaperDetails.observeAsState()

    ScrollableColumn(modifier = Modifier.fillMaxSize(), children = {
        wallpaperDetails?.let { data ->
            Column(modifier = Modifier.fillMaxHeight()) {
                CoilImage(
                    data = data.thumbs.small,
                    modifier = Modifier.aspectRatio(data.ratio.toFloat()).fillMaxSize()
                )

                Column(modifier = Modifier.padding(24.dp)) {

                    //Currently broken in Compose
                    var textValue by state { TextFieldValue("") }
                    FilledTextField(value = textValue,
                        label = { Text(text = "City, State or Zip") },
                        modifier = Modifier.fillMaxWidth(),
                        // Update value of textValue with the latest value of the text field
                        onFocusChanged = {
                            textValue = TextFieldValue("")
                        },
                        onValueChange = {
                            textValue = it
                        },
                        imeAction = ImeAction.Search,
                        onImeActionPerformed = { imeAction, _ ->
                            if (imeAction == ImeAction.Search) {
                                viewmodel.getWallpaperDetails(textValue.text)
                            }
                        }
                    )

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
    })

//    val onPopupDismissed = { viewmodel._newSearch.postValue(false) }
//
//    if (startNewSearch!!) {
//        showSearchDialog(onPopupDismissed, viewModel = viewmodel)
//    }

}