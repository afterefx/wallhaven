package app.androiddev.wallhaven.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.androiddev.wallhaven.ui.appcontainer.AppAction
import app.androiddev.wallhaven.ui.appcontainer.AppContent
import app.androiddev.wallhaven.ui.appcontainer.AppVmOperation
import java.lang.reflect.Modifier

@Composable
fun ProfileContent(onLogout: () -> Unit) {
    Column() {
        Text(text = "Profile")
        TextButton(onClick = {
            onLogout()
        }) {
            Text(text = "Logout")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ProfilePreview() {
    ProfileContent {}
}