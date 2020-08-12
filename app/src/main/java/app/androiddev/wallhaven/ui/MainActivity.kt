package app.androiddev.wallhaven.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.vectorResource
import app.androiddev.wallhaven.R
import app.androiddev.wallhaven.theme.WallHavenTheme
import app.androiddev.wallhaven.ui.details.WallPaperDetailsCompose
import app.androiddev.wallhaven.ui.latest.LatestWallPapersViewModel
import app.androiddev.wallhaven.ui.latest.LatestContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var wallpaperDetailsCompose: WallPaperDetailsCompose
    private val latestVM: LatestWallPapersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WallHavenTheme {
                Container(wallpaperDetailsCompose, latestVM)
            }
        }
    }
}


@Composable
fun Container(
    wallPaperDetailsCompose: WallPaperDetailsCompose,
    latestVM: LatestWallPapersViewModel
) {
    var activityViewState = MainActivityViewState(ScreenState.Latest)
    var currentScreen by remember { mutableStateOf(activityViewState.currentScreen) }
    var wallpaperId by remember { mutableStateOf("dgrgql") }

    Scaffold(
        topBar = {
            TitleContent(currentScreen = activityViewState.currentScreen)
        },
        bodyContent = {
            BodyContent(
                currentScreen = currentScreen,
                updateScreen = { newScreen -> currentScreen = newScreen },
                detailsCompose = wallPaperDetailsCompose,
                id = wallpaperId,
                updateWallpaperId = { newId -> wallpaperId = newId },
                modifier = Modifier.padding(it),
                latestWallPapersViewModel = latestVM
            )
        },
        bottomBar = {
            when (currentScreen) {
                ScreenState.Latest -> {
                    BottomNavigation(
                        content = {
                            listOf(
                                BottomNavigationItem(
                                    selected = currentScreen == ScreenState.Latest,
                                    text = { Text(text = "Latest") },
                                    onSelected = {
                                        currentScreen = ScreenState.Latest
                                    },
                                    icon = { Icon(asset = vectorResource(id = R.drawable.ic_home_white_24dp)) }
                                ),
                                BottomNavigationItem(
                                    selected = currentScreen == ScreenState.Detail,
                                    text = { Text(text = "Detail") },
                                    onSelected = {
                                        currentScreen = ScreenState.Detail
                                    },
                                    icon = { Icon(asset = vectorResource(id = R.drawable.ic_favorite_white_24dp)) }
                                ),
//                                BottomNavigationItem(
//                                    selected = currentScreen == MainActivityScreen.Forecast,
//                                    text = { Text(text = "Forecast") },
//                                    onSelected = {
//                                        currentScreen = MainActivityScreen.Forecast
//                                    },
//                                    icon = { Icon(asset = vectorResource(id = R.drawable.ic_chat_white_24dp)) }
//                                ),
                            )
                        }
                    )
                }
            }
        }

    )
}


@ExperimentalCoroutinesApi
@Composable
fun BodyContent(
    currentScreen: ScreenState,
    updateScreen: (ScreenState) -> Unit,
    detailsCompose: WallPaperDetailsCompose,
    id: String = "dgrgql",
    updateWallpaperId: (String) -> Unit,
    modifier: Modifier = Modifier,
    latestWallPapersViewModel: LatestWallPapersViewModel
) {
    when (currentScreen) {
        ScreenState.Latest -> LatestContent(
            latestWallPapersViewModel,
            updateScreen,
            updateWallpaperId
        )
        ScreenState.Detail -> {
            detailsCompose.WallPaperDetailsContent(id, modifier)
        }
//        MainActivityScreen.Forecast -> ForecastContent(modifier)
    }
}

@Composable
fun TitleContent(currentScreen: ScreenState, modifier: Modifier = Modifier) {
    when (currentScreen) {
        ScreenState.Latest -> TopAppBar("Latest")
        ScreenState.Detail -> TopAppBar("Details")
//        ScreenState.Forecast -> TopAppBar(currentScreen)
    }
}

@Composable
fun TopAppBar(title: String) {
    TopAppBar(
        title = { Text(text = title) }
    )
}

