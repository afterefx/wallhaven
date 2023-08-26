package app.androiddev.wallhaven.auth

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

private const val key = "API_KEY"

class LoginController @Inject constructor(val apiKeyController: IApiKeyController) : ILoginController {

    override val isLoggedIn: MutableStateFlow<Boolean>
        get() = _isLoggedIn

    private val _isLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        _isLoggedIn.value = apiKeyController.exists()
    }

    override fun login(apiKey: String) {
        apiKeyController.setApiKey(apiKey)
        _isLoggedIn.value = true
    }

    override fun logout() {
        apiKeyController.deleteApiKey()
        _isLoggedIn.value = false
    }
}

interface ILoginController {
    val isLoggedIn: MutableStateFlow<Boolean>
    fun login(apiKey: String)
    fun logout()
}