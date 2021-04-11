package app.androiddev.wallhaven.ui

import android.content.SharedPreferences
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.androiddev.wallhaven.R
import app.androiddev.wallhaven.extensions.toColor
import app.androiddev.wallhaven.theme.ColorState
import app.androiddev.wallhaven.theme.DarkColorPalette
import app.androiddev.wallhaven.theme.DynamicTheme
import app.androiddev.wallhaven.ui.appcontainer.AppContainer
import app.androiddev.wallhaven.ui.appcontainer.AppContent
import app.androiddev.wallhaven.ui.appcontainer.BottomNavigation
import app.androiddev.wallhaven.ui.appcontainer.TitleContent
import app.androiddev.wallhaven.ui.details.WallPaperDetailsContent
import app.androiddev.wallhaven.ui.latest.LatestContent
import app.androiddev.wallhaven.ui.login.LoginUi
import app.androiddev.wallhaven.ui.random.RandomContent
import app.androiddev.wallhaven.ui.toplist.TopList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun WallHavenApp(sharedPref: SharedPreferences) {
    var showUI by remember { mutableStateOf(false) }

    showUI = sharedPref.contains(stringResource(id = R.string.API_KEY))
    if (showUI) {
        AppContainer()
    } else {
        val stringResource = stringResource(id = R.string.API_KEY)
        val saveApiToken: (String) -> Unit = {
            sharedPref.edit().putString(stringResource, it).apply()
            showUI = true
        }
        LoginUi(saveApiToken)
    }
}
