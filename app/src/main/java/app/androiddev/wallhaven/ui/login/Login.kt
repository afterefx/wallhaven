package app.androiddev.wallhaven.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.androiddev.wallhaven.extensions.toColor

@Composable
fun LoginUi(saveApiToken: (String) -> Unit = {}) {
    Surface(color = "#0D4390".toColor(), modifier = Modifier.fillMaxSize()) {

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
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                shape = MaterialTheme.shapes.medium,
                colors = textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    disabledTextColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                shape = MaterialTheme.shapes.medium,
                visualTransformation = PasswordVisualTransformation(),
                colors = textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    disabledTextColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )

            val vm: LoginViewModel = viewModel()
            val onSubmit = {
                vm.doLogin(username, password, saveApiToken)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(modifier = Modifier.padding(top = 4.dp), onClick = onSubmit) {
                    Text(text = "Submit")
                }
            }
        }

    }
}

