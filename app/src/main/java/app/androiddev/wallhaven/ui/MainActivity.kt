package app.androiddev.wallhaven.ui

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
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import app.androiddev.wallhaven.R
import app.androiddev.wallhaven.customcomposables.WebComponent
import app.androiddev.wallhaven.customcomposables.WebContext
import app.androiddev.wallhaven.theme.WallHavenTheme
import app.androiddev.wallhaven.ui.current.LatestContent
import app.androiddev.wallhaven.ui.current.LatestWallPapersViewModel
import app.androiddev.wallhaven.ui.current.WallPaperDetailsContent
import app.androiddev.wallhaven.ui.current.WallPaperDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: WallPaperDetailsViewModel by viewModels()
    private val latestVM: LatestWallPapersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WallHavenTheme {
                Container(viewModel, latestVM)
            }
        }
    }
}



@Composable
fun Container(viewModel: WallPaperDetailsViewModel, latestVM: LatestWallPapersViewModel) {
    var currentScreen by state { MainActivityScreen.Latest }
    Scaffold(
        topBar = {
            TitleContent(currentScreen = currentScreen)
        },
        bodyContent = {
            BodyContent(
                currentScreen = currentScreen,
                modifier = Modifier.padding(it),
                viewmodel = viewModel,
                latestWallPapersViewModel = latestVM
            )
        },
        bottomBar = {
            if (currentScreen != MainActivityScreen.Details) {
                BottomNavigation(
                    content = {
                        listOf(
                            BottomNavigationItem(
                                selected = currentScreen == MainActivityScreen.Latest,
                                text = { Text(text = "Latest") },
                                onSelected = {
                                    currentScreen = MainActivityScreen.Latest
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
        }

    )
}

enum class MainActivityScreen {
    Latest,
    Details,
    Forecast,
}

@Composable
fun BodyContent(
    currentScreen: MainActivityScreen,
    modifier: Modifier = Modifier,
    viewmodel: WallPaperDetailsViewModel,
    latestWallPapersViewModel: LatestWallPapersViewModel
) {
    when (currentScreen) {
        MainActivityScreen.Latest -> LatestContent(currentScreen, latestWallPapersViewModel)
        MainActivityScreen.Details -> WallPaperDetailsContent(currentScreen, modifier, viewmodel)
        MainActivityScreen.Forecast -> ForecastContent(modifier)
    }
}

@Composable
fun TitleContent(currentScreen: MainActivityScreen, modifier: Modifier = Modifier) {
    when (currentScreen) {
        MainActivityScreen.Latest -> TopAppBar(currentScreen)
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
