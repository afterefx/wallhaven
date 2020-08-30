package app.androiddev.wallhaven.ui.login

import android.content.SharedPreferences
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.viewModel
import app.androiddev.wallhaven.R
import app.androiddev.wallhaven.extensions.Color
import app.androiddev.wallhaven.ui.LoginViewModel

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