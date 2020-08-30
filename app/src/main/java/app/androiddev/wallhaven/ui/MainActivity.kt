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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import app.androiddev.wallhaven.R
import app.androiddev.wallhaven.extensions.Color
import app.androiddev.wallhaven.theme.ColorState
import app.androiddev.wallhaven.theme.DarkColorPalette
import app.androiddev.wallhaven.theme.DynamicTheme
import app.androiddev.wallhaven.ui.details.WallPaperDetailsCompose
import app.androiddev.wallhaven.ui.latest.LatestContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            if (showUI) {
                Container(wallpaperDetailsCompose)
            } else {
                LoginUi(sharedPref) {
                    showUI = it
                }
            }
        }
    }
}

@Composable
fun LoginUi(
    sharedPref: SharedPreferences,
    updateShowUi: (Boolean) -> Unit
) {
    Surface(color = Color("#0D4390"), modifier = Modifier.fillMaxSize()) {

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
            var username by remember { mutableStateOf(TextFieldValue("")) }
            var password by remember { mutableStateOf(TextFieldValue("")) }

            TextField(
                value = username,
                onValueChange = { s: TextFieldValue -> username = s },
                label = @Composable { Text(text = "Username") },
                shape = MaterialTheme.shapes.medium,
                backgroundColor = Color.White,
                activeColor = Color.White,
                inactiveColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = password,
                onValueChange = { s: TextFieldValue -> password = s },
                label = @Composable { Text(text = "Password") },
                shape = MaterialTheme.shapes.medium,
                visualTransformation = PasswordVisualTransformation(),
                backgroundColor = Color.White,
                activeColor = Color.White,
                inactiveColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            val vm: LoginViewModel = viewModel()
            val stringResource = stringResource(id = R.string.API_KEY)
            val onSubmit = {
                vm.doLogin(username.text, password.text) {
                    sharedPref.edit().putString(stringResource, it).apply()
                    updateShowUi(true)
                }
            }

            Button(modifier = Modifier.padding(top = 4.dp), onClick = onSubmit) {
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

    val colors = remember { ColorState(DarkColorPalette.primary, DarkColorPalette.onPrimary) }

    DynamicTheme(colors = colors) {
        val scope = rememberCoroutineScope()
        scope.launch(Dispatchers.IO) {
            Thread.sleep(3000)
            colors.updateColors(Color.Red, Color.Blue)
        }
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

