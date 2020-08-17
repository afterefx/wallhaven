package app.androiddev.wallhaven.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.androiddev.wallhaven.R
import app.androiddev.wallhaven.theme.WallHavenTheme
import app.androiddev.wallhaven.ui.details.WallPaperDetailsCompose
import app.androiddev.wallhaven.ui.latest.LatestContent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var wallpaperDetailsCompose: WallPaperDetailsCompose

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var showUI by remember { mutableStateOf(false) }
            val sharedPref =
                getSharedPreferences(getString(R.string.sharedpref), Context.MODE_PRIVATE)

            showUI = sharedPref.contains(getString(R.string.API_KEY))
            WallHavenTheme {
                if (showUI) {
                    Container(wallpaperDetailsCompose)
                } else {
                    ApiUi(sharedPref) {
                        showUI = it
                    }
                }

            }
        }
    }
}

@Composable
fun ApiUi(
    sharedPref: SharedPreferences,
    updateShowUi: (Boolean) -> Unit
) {
    Surface(color = Color(0xFF0D4390), modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(90.dp))
            Text(
                "Wallhaven",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 60.sp
            )
            Spacer(modifier = Modifier.height(90.dp))
            var tf by remember { mutableStateOf(TextFieldValue("")) }

            TextField(
                value = tf,
                onValueChange = { s: TextFieldValue -> tf = s },
                label = @Composable { Text(text = "API Key") },
                shape = MaterialTheme.shapes.medium,
                backgroundColor = Color.White,
                activeColor = Color.White,
                inactiveColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            val stringResource = stringResource(id = R.string.API_KEY)
            Button(modifier = Modifier.padding(top = 4.dp), onClick = {
                sharedPref.edit().putString(stringResource, tf.text).apply()
                updateShowUi(true)
            }) {
                Text(text = "Submit")
            }
        }

    }
}

@Composable
fun Container(wallPaperDetailsCompose: WallPaperDetailsCompose) {

    var activityViewState = MainActivityViewState(ScreenState.Latest)
    var currentScreen by remember { mutableStateOf(activityViewState.currentScreen) }
    var wallpaperId by remember { mutableStateOf("dgrgql") }

    val updateScreen: (ScreenState) -> Unit = { newScreen ->
        currentScreen = newScreen
        activityViewState.currentScreen = newScreen
    }
    val updateWallpaper: (String) -> Unit = { newId -> wallpaperId = newId }

    Scaffold(
        topBar = {
            TitleContent(currentScreen = currentScreen, updateScreen = updateScreen)
        },
        bodyContent = {
            when (currentScreen) {
                ScreenState.Latest -> {
                    LatestContent(updateScreen, updateWallpaper)
                }
                ScreenState.Detail -> {
                    wallPaperDetailsCompose.WallPaperDetailsContent(
                        wallpaperId,
                        Modifier.padding(it)
                    )
                }
                ScreenState.TopList -> TODO()
                ScreenState.Random -> TODO()
            }
        },
        bottomBar = {
            when (currentScreen) {
                ScreenState.Latest -> {
                    BottomNavigation(currentScreen = currentScreen, updateScreen = updateScreen)
                }
                ScreenState.TopList -> {
                    BottomNavigation(currentScreen = currentScreen, updateScreen = updateScreen)
                }
                ScreenState.Random -> {
                    BottomNavigation(currentScreen = currentScreen, updateScreen = updateScreen)
                }
                ScreenState.Detail -> {
                    //no bottom bar
                }
            }
        }

    )
}

@Composable
fun BottomNavigation(currentScreen: ScreenState, updateScreen: (ScreenState) -> Unit) {
    BottomNavigation(
        content = {
            listOf(
                BottomNavigationItem(
                    selected = currentScreen == ScreenState.Latest,
                    label = { Text(text = "Latest") },
                    onSelect = {
                        updateScreen(ScreenState.Latest)
                    },
                    icon = { Icon(asset = vectorResource(id = R.drawable.ic_home_white_24dp)) }
                ),
                BottomNavigationItem(
                    selected = currentScreen == ScreenState.TopList,
                    label = { Text(text = "Top List") },
                    onSelect = {
                        updateScreen(ScreenState.TopList)
                    },
                    icon = { Icon(asset = vectorResource(id = R.drawable.ic_favorite_white_24dp)) }
                ),
                BottomNavigationItem(
                    selected = currentScreen == ScreenState.Random,
                    label = { Text(text = "Random") },
                    onSelect = {
                        updateScreen(ScreenState.Random)
                    },
                    icon = { Icon(asset = vectorResource(id = R.drawable.ic_random)) }
                )
            )
        }
    )
}


@Composable
fun TitleContent(
    currentScreen: ScreenState,
    modifier: Modifier = Modifier,
    updateScreen: (ScreenState) -> Unit
) {
    when (currentScreen) {
        ScreenState.Latest -> TopAppBar("Latest")
        ScreenState.Detail -> TopAppBar("Details") {
            IconButton(onClick = {
                updateScreen(ScreenState.Latest)
            }) {
                Icon(asset = vectorResource(id = R.drawable.ic_arrow_back))
            }
        }
    }
}

@Composable
fun TopAppBar(title: String, navigationIcon: @Composable (() -> Unit)? = null) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon
    )
}

