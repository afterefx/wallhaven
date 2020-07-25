package com.mcwilliams.letscompose.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.input.ImeAction
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import com.mcwilliams.letscompose.R
import com.mcwilliams.letscompose.customcomposables.WebComponent
import com.mcwilliams.letscompose.customcomposables.WebContext
import com.mcwilliams.letscompose.theme.LetsComposeTheme
import com.mcwilliams.letscompose.ui.current.CurrentWeatherContent
import com.mcwilliams.letscompose.ui.current.CurrentWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CurrentWeatherViewModel by viewModels()

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
fun Container(viewModel: CurrentWeatherViewModel) {
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
    viewmodel: CurrentWeatherViewModel
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
fun ForecastContent(modifier: Modifier) {
    Column(modifier = modifier.height(600.dp)) {
        WebComponent(url = "https://www.google.com", webContext = WebContext())
    }
}
