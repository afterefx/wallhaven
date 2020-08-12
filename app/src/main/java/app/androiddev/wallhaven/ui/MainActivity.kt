package app.androiddev.wallhaven.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.res.vectorResource
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
            WallHavenTheme {
                Container(wallpaperDetailsCompose)
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
                    text = { Text(text = "Latest") },
                    onSelected = {
                        updateScreen(ScreenState.Latest)
                    },
                    icon = { Icon(asset = vectorResource(id = R.drawable.ic_home_white_24dp)) }
                ),
                BottomNavigationItem(
                    selected = currentScreen == ScreenState.TopList,
                    text = { Text(text = "Top List") },
                    onSelected = {
                        updateScreen(ScreenState.TopList)
                    },
                    icon = { Icon(asset = vectorResource(id = R.drawable.ic_favorite_white_24dp)) }
                ),
                BottomNavigationItem(
                    selected = currentScreen == ScreenState.Random,
                    text = { Text(text = "Random") },
                    onSelected = {
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

