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
        floatingActionButton = {
            if (currentScreen == MainActivityScreen.Current) {
                FloatingActionButton(onClick = {
                    viewModel.startNewSearch()
                }, icon = { Image(asset = Icons.Default.Search) })
            }
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
    val startNewSearch by viewmodel.newSearch.observeAsState()

    ScrollableColumn(modifier = modifier, children = {
        Column(modifier = Modifier.padding(8.dp)) {
            weatherData?.let {
                Text(
                    text = "Current Temperature in ${it.name} ",
                    style = MaterialTheme.typography.h6
                )
                Text(text = "${it.main.temp} °F", style = MaterialTheme.typography.h5)
            }
        }
    })

    val onPopupDismissed = { viewmodel._newSearch.postValue(false) }

    if (startNewSearch!!) {
        showSearchDialog(onPopupDismissed, viewModel = viewmodel)
    }

}

@Composable
fun showSearchDialog(onPopupDismissed: () -> Unit, viewModel: LocationViewModel) {
    val AlertDialogWidth = Modifier.preferredWidth(312.dp)
    val TitlePadding = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 0.dp)
    val emphasisLevels = EmphasisAmbient.current

    Dialog(onCloseRequest = onPopupDismissed) {
        Surface(
            modifier = AlertDialogWidth,
            shape = MaterialTheme.shapes.medium
        ) {
            Column() {
                Box(TitlePadding.gravity(Alignment.Start)) {
                    ProvideEmphasis(emphasisLevels.high) {
                        val textStyle = MaterialTheme.typography.subtitle1
                        ProvideTextStyle(textStyle) { Text(text = "Search") }
                    }
                }

                Spacer(Modifier.preferredHeight(14.dp))

                //Currently broken in Compose
                var textValue by state { TextFieldValue("") }
                FilledTextField(value = textValue,
                    label = { Text(text = "City, State or Zip") },
                    modifier = Modifier.padding (16.dp) + Modifier.fillMaxWidth(),
                    // Update value of textValue with the latest value of the text field
                    onFocusChanged = {
                        textValue = TextFieldValue("")
                    },
                    onValueChange = {
                        textValue = it
                    }
                )

                Spacer(Modifier.preferredHeight(28.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onPopupDismissed
                    ) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.preferredWidth(8.dp))
                    TextButton(
                        onClick = {
                            viewModel.search(textValue.text)
                        }
                    ) {
                        Text(text = "Search")
                    }
                }
            }
        }
    }
//    AlertDialog(
//        onCloseRequest = onPopupDismissed,
//        text = {
//            Text("Congratulations! You just clicked the text successfully")
//        },
//        confirmButton = {
//            // Button is a pre-defined Material Design implementation of a contained button -
//            // https://material.io/design/components/buttons.html#contained-button.
//            Button(
//                onClick = onPopupDismissed
//            ) {
//                // The Button composable allows you to provide child composables that inherit
//                // this button functionality.
//                // The Text composable is pre-defined by the Compose UI library; you can use this
//                // composable to render text on the screen
//                Text(text = "Ok")
//            }
//        })
}

@Composable
fun ForecastContent(modifier: Modifier) {
    Column(modifier = modifier.height(600.dp)) {
        WebComponent(url = "https://www.google.com", webContext = WebContext())
    }
}
