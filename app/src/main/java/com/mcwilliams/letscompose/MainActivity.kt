package com.mcwilliams.letscompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
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
        MainActivityScreen.Forecast -> ForcastContent(modifier)
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
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = "Current Temperature: ")
            weatherData?.let {
                Text(text = it.main.temp.toString())
            }
        }
    })
}

@Composable
fun ForcastContent(modifier: Modifier) {
    Column(modifier = modifier.height(600.dp)) {
        WebComponent(url = "https://www.google.com", webContext = WebContext())
    }
}
