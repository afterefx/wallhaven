package app.androiddev.wallhaven.ui

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import app.androiddev.wallhaven.R
import app.androiddev.wallhaven.extensions.Color
import app.androiddev.wallhaven.theme.ColorState
import app.androiddev.wallhaven.theme.DarkColorPalette
import app.androiddev.wallhaven.theme.DynamicTheme
import app.androiddev.wallhaven.ui.details.WallPaperDetailsContent
import app.androiddev.wallhaven.ui.latest.LatestContent
import app.androiddev.wallhaven.ui.random.RandomContent
import app.androiddev.wallhaven.ui.toplist.TopList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AppContainer() {
    var activityViewState = MainActivityViewState(ScreenState.Latest)
    var currentScreen by remember { mutableStateOf(activityViewState.currentScreen) }
    var previousScreen by remember { mutableStateOf(activityViewState.currentScreen) }
    var wallpaperId by remember { mutableStateOf("dgrgql") }

    val updateScreen: (ScreenState) -> Unit = { newScreen ->
        previousScreen = currentScreen
        currentScreen = newScreen
        activityViewState.currentScreen = newScreen
    }
    val updateWallpaper: (String) -> Unit = { newId -> wallpaperId = newId }

    val colors = remember { ColorState(DarkColorPalette.primary, DarkColorPalette.onPrimary) }

    val latestScrollState = rememberScrollState(0f)
    val topListScrollState = rememberScrollState(0f)
    val randomScrollState = rememberScrollState(0f)

    DynamicTheme(colors = colors) {
        val scope = rememberCoroutineScope()
        scope.launch(Dispatchers.IO) {
            Thread.sleep(3000)
            colors.updateColors(Color("#232323"), Color.White)
        }
        Scaffold(
            topBar = {
                TitleContent(
                    currentScreen = currentScreen,
                    prev = previousScreen,
                    updateScreen = updateScreen
                )
            },
            bodyContent = {
                when (currentScreen) {
                    ScreenState.Latest -> {
                        LatestContent(updateScreen, updateWallpaper, latestScrollState)
                    }
                    ScreenState.Detail -> {
                        WallPaperDetailsContent(
                            wallpaperId,
                            updateScreen,
                            Modifier.padding(it)
                        )
                    }
                    ScreenState.TopList -> {
                        TopList(updateScreen, updateWallpaper, topListScrollState)
                    }
                    ScreenState.Random -> {
                        RandomContent(updateScreen, updateWallpaper, randomScrollState)
                    }
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
}

@Composable
fun BottomNavigation(currentScreen: ScreenState, updateScreen: (ScreenState) -> Unit) {
    androidx.compose.material.BottomNavigation(
        content = {
            listOf(
                BottomNavigationItem(
                    selected = currentScreen == ScreenState.Latest,
                    label = { Text(text = "Latest") },
                    onClick = {
                        updateScreen(ScreenState.Latest)
                    },
                    icon = { Icon(asset = vectorResource(id = R.drawable.ic_home_white_24dp)) }
                ),
                BottomNavigationItem(
                    selected = currentScreen == ScreenState.TopList,
                    label = { Text(text = "Top List") },
                    onClick = {
                        updateScreen(ScreenState.TopList)
                    },
                    icon = { Icon(asset = vectorResource(id = R.drawable.ic_favorite_white_24dp)) }
                ),
                BottomNavigationItem(
                    selected = currentScreen == ScreenState.Random,
                    label = { Text(text = "Random") },
                    onClick = {
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
    prev: ScreenState,
    modifier: Modifier = Modifier,
    updateScreen: (ScreenState) -> Unit
) {
    when (currentScreen) {
        ScreenState.Latest -> TopAppBar("Latest")
        ScreenState.TopList -> TopAppBar("Top")
        ScreenState.Random -> TopAppBar("Random")
        ScreenState.Detail -> TopAppBar("Details") {
            IconButton(onClick = {
                updateScreen(prev)
            }) {
                Icon(asset = vectorResource(id = R.drawable.ic_arrow_back))
            }
        }
    }
}

@Composable
fun TopAppBar(title: String, navigationIcon: @Composable (() -> Unit)? = null) {
    androidx.compose.material.TopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon
    )
}

