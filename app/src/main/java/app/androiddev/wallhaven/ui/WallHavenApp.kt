package app.androiddev.wallhaven.ui

import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import app.androiddev.wallhaven.R
import app.androiddev.wallhaven.ui.login.LoginUi

@Composable
fun WallHavenApp(sharedPref: SharedPreferences) {
    var showUI by remember { mutableStateOf(false) }

    showUI = sharedPref.contains(stringResource(id = R.string.API_KEY))
    if (showUI) {
        AppContainer()
    } else {
        LoginUi(sharedPref) {
            showUI = it
        }
    }
}
