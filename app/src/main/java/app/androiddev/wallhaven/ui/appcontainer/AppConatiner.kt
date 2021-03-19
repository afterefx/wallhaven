package app.androiddev.wallhaven.ui.appcontainer

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Shuffle
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import app.androiddev.wallhaven.R
import app.androiddev.wallhaven.extensions.toColor
import app.androiddev.wallhaven.theme.ColorState
import app.androiddev.wallhaven.theme.DarkColorPalette
import app.androiddev.wallhaven.theme.DynamicTheme
import app.androiddev.wallhaven.ui.ScreenState
import app.androiddev.wallhaven.ui.details.WallPaperDetailsContent
import app.androiddev.wallhaven.ui.latest.LatestContent
import app.androiddev.wallhaven.ui.random.RandomContent
import app.androiddev.wallhaven.ui.toplist.TopList
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

const val LATEST = "Latest"
const val DETAILS = "Details"
const val RANDOM = "Random"
const val TOP = "Top"
const val TOP_LIST = "Top List"

@Composable
fun AppContainer() {
    AppContent { _, vs ->
        val colors = remember { ColorState(DarkColorPalette.primary, DarkColorPalette.onPrimary) }

        DynamicTheme(colors = colors) {
            val scope = rememberCoroutineScope()
            scope.launch(Dispatchers.IO) {
                Thread.sleep(3000)
                colors.updateColors("#232323".toColor(), Color.White)
            }
            Scaffold(
                topBar = { TitleContent() },
                content = {
                    when (vs.currentScreen) {
                        ScreenState.Latest -> {
                            LatestContent()
                        }
                        is ScreenState.Detail -> {
                            WallPaperDetailsContent()
                        }
                        ScreenState.TopList -> {
                            TopList()
                        }
                        ScreenState.Random -> {
                            RandomContent()
                        }
                    }
                },
                bottomBar = {
                    when (vs.currentScreen) {
                        ScreenState.Latest,
                        ScreenState.TopList,
                        ScreenState.Random ->
                            BottomNavigation()
                        is ScreenState.Detail -> {
                            //no bottom bar
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavigation() {
    androidx.compose.material.BottomNavigation(
        content = {
            listOf(
                NavigationIcon(
                    screen = ScreenState.Latest,
                    label = LATEST,
                    icon = Icons.Sharp.Home
                ),
                NavigationIcon(
                    screen = ScreenState.TopList,
                    label = TOP_LIST,
                    icon = Icons.Sharp.Favorite
                ),
                NavigationIcon(
                    screen = ScreenState.Random,
                    label = RANDOM,
                    icon = Icons.Sharp.Shuffle
                )
            )
        }
    )
}

@Composable
fun RowScope.NavigationIcon(
    screen: ScreenState,
    label: String,
    icon: ImageVector
) {
    AppContent { vm, vs ->
        BottomNavigationItem(
            selected = vs.currentScreen == screen,
            label = { Text(text = label) },
            onClick = { vm.navigate(screen) },
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = label
                )
            }
        )
    }
}

@Composable
fun TitleContent() {
    AppContent { vm, vs ->
        when (vs.currentScreen) {
            ScreenState.Latest -> TopAppBar(LATEST)
            ScreenState.TopList -> TopAppBar(TOP)
            ScreenState.Random -> TopAppBar(RANDOM)
            is ScreenState.Detail -> {
                TopAppBar(DETAILS) {
                    IconButton(onClick = {
                        vm.goBack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = DETAILS
                        )
                    }
                }
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

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun AppContent(
    content: @Composable (
        viewModel: AppContainerViewModel,
        appViewState: AppContainerViewState,
    ) -> Unit
) {
    val vm: AppContainerViewModel = mavericksViewModel()
    val appViewState by vm.collectAsState()

    if (appViewState.latestListState == null
        || appViewState.topListState == null
        || appViewState.randomListState == null
    ) {
        vm.initializeListStates(
            latest = rememberLazyListState(),
            top = rememberLazyListState(),
            random = rememberLazyListState()
        )
    }
    content(vm, appViewState)
}
