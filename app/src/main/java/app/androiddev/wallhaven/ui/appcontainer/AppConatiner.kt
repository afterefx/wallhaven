package app.androiddev.wallhaven.ui.appcontainer

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.androiddev.wallhaven.extensions.toColor
import app.androiddev.wallhaven.theme.ColorState
import app.androiddev.wallhaven.theme.DarkColorPalette
import app.androiddev.wallhaven.theme.DynamicTheme
import app.androiddev.wallhaven.ui.Screen
import app.androiddev.wallhaven.ui.details.WallPaperDetailsContent
import app.androiddev.wallhaven.ui.getScreen
import app.androiddev.wallhaven.ui.latest.LatestContent
import app.androiddev.wallhaven.ui.random.RandomContent
import app.androiddev.wallhaven.ui.toplist.TopList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@Composable
fun NavHostController.currentRoute(): String? {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun AppContainer() {
    val colors = remember { ColorState(DarkColorPalette.primary, DarkColorPalette.onPrimary) }

    val navController = rememberNavController()
    DynamicTheme(colors = colors) {
        val scope = rememberCoroutineScope()
        scope.launch(Dispatchers.IO) {
            Thread.sleep(3000)
            colors.updateColors("#232323".toColor(), Color.White)
        }
        Scaffold(
            topBar = { TitleContent(navController) },
            content = {
                NavHost(navController = navController, startDestination = Screen.Latest.route) {
                    composable(Screen.Latest.route) {
                        LatestContent(navController)
                    }
                    composable(Screen.Detail.route) { backStackEntry ->
                        WallPaperDetailsContent(
                            navController,
                            backStackEntry.arguments?.getString("id") ?: ""
                        )
                    }
                    composable(Screen.TopList.route) {
                        TopList(navController)
                    }
                    composable(Screen.Random.route) {
                        RandomContent(navController)
                    }
                }
            },
            bottomBar = {
                when (navController.currentRoute()) {
                    Screen.Latest.route,
                    Screen.TopList.route,
                    Screen.Random.route ->
                        BottomNavigation(navController)
                    Screen.Detail.route -> {
                        //no bottom bar
                    }
                }
            }
        )
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        Screen.Latest,
        Screen.TopList,
        Screen.Random
    )
    androidx.compose.material.BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                selected = navController.currentRoute() == screen.route,
                label = { Text(text = screen.title) },
                onClick = { navController.navigate(screen.route) },
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) }
            )
        }
    }
}

@Composable
fun TitleContent(navController: NavHostController) {
    val screen = getScreen(navController.currentRoute())
    TopAppBar(
        title = screen.title,
        navigationIcon = if (screen.navUp) {
            {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(rememberVectorPainter(screen.icon), screen.title)
                }
            }
        } else null
    )
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
    val vm: AppContainerViewModel = hiltViewModel()
    val appViewState by vm.state.collectAsState()

    if (appViewState.latestListState == null
        || appViewState.topListState == null
        || appViewState.randomListState == null
    ) {
        AppAction.action(
            AppVmOperation.InitializeListStates(
                latest = rememberLazyListState(),
                top = rememberLazyListState(),
                random = rememberLazyListState()
            ),
            vm
        )
    }
    content(vm, appViewState)
}
