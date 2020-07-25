package com.mcwilliams.letscompose.ui.current

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.input.ImeAction
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.mcwilliams.letscompose.ui.MainActivityScreen
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CurrentWeatherContent(
    currentScreen: MainActivityScreen,
    modifier: Modifier,
    viewmodel: CurrentWeatherViewModel
) {
    val weatherData by viewmodel.weatherData.observeAsState()

    ScrollableColumn(modifier = modifier, children = {
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
                        viewmodel.getWeatherData(textValue.text)
                    }
                }
            )

            Spacer(modifier = Modifier.preferredHeight(8.dp))

            weatherData?.let {
                val currentDateTime = LocalDateTime.now()
                val formatter: DateTimeFormatter =
                    DateTimeFormatter.ofPattern("MMMM dd, hh:mm a")
                val formatDateTime: String = currentDateTime.format(formatter)
                Text(
                    text = formatDateTime,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "Current Temperature in ${it.name} ",
                    style = MaterialTheme.typography.h6
                )
                Text(text = "${it.main.temp} °F", style = MaterialTheme.typography.h5)
            }
        }
    })

//    val onPopupDismissed = { viewmodel._newSearch.postValue(false) }
//
//    if (startNewSearch!!) {
//        showSearchDialog(onPopupDismissed, viewModel = viewmodel)
//    }

}