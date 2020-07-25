package com.mcwilliams.letscompose.ui.current

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.ScrollableRow
import androidx.ui.foundation.Text
import androidx.ui.input.ImeAction
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import com.mcwilliams.letscompose.ui.MainActivityScreen
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun CurrentWeatherContent(
    currentScreen: MainActivityScreen,
    modifier: Modifier,
    viewmodel: CurrentWeatherViewModel
) {
    val weatherData by viewmodel.weatherData.observeAsState()

    ScrollableColumn(modifier = modifier.fillMaxSize(), children = {
        Column(modifier = Modifier.padding(24.dp).fillMaxHeight()) {
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
                it.current.let { currentWeather ->
                    val currentDateTime = LocalDateTime.now()
                    val formatter: DateTimeFormatter =
                        DateTimeFormatter.ofPattern("MMMM dd, hh:mm a")
                    val formatDateTime: String = currentDateTime.format(formatter)
                    Text(
                        text = formatDateTime,
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "${currentWeather.temp.toInt()}°F",
                        style = MaterialTheme.typography.h2
                    )
                    Text(
                        text = "Feels Like ${currentWeather.feels_like.toInt()}°",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(0.dp, 8.dp)
                    )
                    Text(
                        text = currentWeather.weather[0].description,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(0.dp, 8.dp)
                    )
                }

                Spacer(modifier = Modifier.preferredHeight(60.dp))

                ScrollableRow(verticalGravity = Alignment.Bottom) {
                    Row() {
                        it.hourly.forEach { hourly ->
                            val localTime = LocalDateTime.ofInstant(
                                Instant.ofEpochSecond(hourly.dt),
                                TimeZone.getDefault().toZoneId()
                            )

                            Column(modifier = modifier.padding(8.dp), horizontalGravity = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${hourly.temp.toInt()}°F",
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = localTime.format(DateTimeFormatter.ofPattern("hh a")),
                                    modifier = Modifier.padding(0.dp, 8.dp),
                                    textAlign = TextAlign.Center
                                )
                            }

                        }
                    }
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