package com.mcwilliams.letscompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.input.ImeAction
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Search
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import com.mcwilliams.letscompose.ui.LetsComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LetsComposeTheme {
                Container(viewModel)
            }
        }
    }
}

@Composable
fun Container(viewModel: LocationViewModel) {
    var currentScreen by state { MainActivityScreen.Current }
    Scaffold(
        topBar = {
            TitleContent(currentScreen = currentScreen)
        },
        bodyContent = {
            BodyContent(
                currentScreen = currentScreen,
                modifier = Modifier.padding(it),
                viewmodel = viewModel
            )
        },
        bottomBar = {
            BottomNavigation(
                content = {
                    listOf(
                        BottomNavigationItem(
                            selected = currentScreen == MainActivityScreen.Current,
                            text = { Text(text = "Current") },
                            onSelected = {
                                currentScreen = MainActivityScreen.Current
                            },
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_home_white_24dp)) }
                        ),
                        BottomNavigationItem(
                            selected = currentScreen == MainActivityScreen.Forecast,
                            text = { Text(text = "Forecast") },
                            onSelected = {
                                currentScreen = MainActivityScreen.Forecast
                            },
                            icon = { Icon(asset = vectorResource(id = R.drawable.ic_chat_white_24dp)) }
                        ),
                    )
                }
            )
        }

    )
}

enum class MainActivityScreen {
    Current,
    Forecast,
}

@Composable
fun BodyContent(
    currentScreen: MainActivityScreen,
    modifier: Modifier = Modifier,
    viewmodel: LocationViewModel
) {
    when (currentScreen) {
        MainActivityScreen.Current -> CurrentWeatherContent(currentScreen, modifier, viewmodel)
        MainActivityScreen.Forecast -> ForecastContent(modifier)
    }
}

@Composable
fun TitleContent(currentScreen: MainActivityScreen, modifier: Modifier = Modifier) {
    when (currentScreen) {
        MainActivityScreen.Current -> TopAppBar(currentScreen)
        MainActivityScreen.Forecast -> TopAppBar(currentScreen)
    }
}

@Composable
fun TopAppBar(currentScreen: MainActivityScreen) {
    TopAppBar(
        title = { Text(text = currentScreen.name) }
    )
}

@Composable
fun CurrentWeatherContent(
    currentScreen: MainActivityScreen,
    modifier: Modifier,
    viewmodel: LocationViewModel
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



@Composable
fun ForecastContent(modifier: Modifier) {
    Column(modifier = modifier.height(600.dp)) {
        WebComponent(url = "https://www.google.com", webContext = WebContext())
    }
}
