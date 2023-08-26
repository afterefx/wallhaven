package app.androiddev.wallhaven.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.androiddev.wallhaven.ui.appcontainer.AppAction
import app.androiddev.wallhaven.ui.appcontainer.AppContainer
import app.androiddev.wallhaven.ui.appcontainer.AppContent
import app.androiddev.wallhaven.ui.appcontainer.AppVmOperation
import app.androiddev.wallhaven.auth.ILoginController
import app.androiddev.wallhaven.ui.login.LoginUi
import app.androiddev.wallhaven.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(
) : AppCompatActivity() {

    @Inject
    lateinit var loginController: ILoginController

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isLoggedIn by loginController.isLoggedIn.collectAsState()

            if (isLoggedIn) AppContainer { loginController.logout() }
            else LoginUi { user, pass ->
                loginViewModel.doLogin(user, pass) { apiKey ->
                    loginController.login(apiKey)
                }
            }
        }
    }
}

