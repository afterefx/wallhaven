package app.androiddev.wallhaven.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LoginViewModel @ViewModelInject constructor() : ViewModel() {

    fun doLogin(username: String, password: String, callback: (String) -> Unit) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val sessionToken = getSessionToken()
            doLogin(sessionToken, username, password)
            callback(getApiToken())
        }
    }

    suspend fun getApiToken(): String {
        val client = getClient()

        val request: Request = Request.Builder()
            .url("https://wallhaven.cc/settings/account")
            .build()

        val response = client.newCall(request).await()
        val body = response.body?.string() ?: ""
        var split = body.substringAfter("<h3>API Key</h3>")
        split = split.substringAfter("readonly=")
        split = split.substringAfter("value=\"")
        val apiToken = split.substringBefore("\"/>")

        return apiToken
    }

    suspend fun doLogin(token: String, username: String, password: String) {
        val client = getClient()

        val formBody = FormBody.Builder()
            .add("_token", token)
            .add("username", username)
            .add("password", password)
            .build()
        val request = Request.Builder()
            .url("https://wallhaven.cc/auth/login")
            .post(formBody)
            .build()

        client.newCall(request).await()
    }

    suspend fun getSessionToken(): String {
        val client = getClient()

        val request: Request = Request.Builder()
            .url("https://wallhaven.cc/login")
            .build();

        var token: String?
        val response = client.newCall(request).await()
        val string = response.body?.string() ?: ""
        var split = string.substring(string.indexOf("_token"))
        split = split.substringBefore("\">")
        token = split.substringAfter("value=\"")
        return token
    }
}

var okclient: OkHttpClient? = null
fun getClient(): OkHttpClient {
    if (okclient == null) {
        val builder = OkHttpClient.Builder()
        builder.cookieJar(object : CookieJar {
            val cookieStore: MutableList<Cookie> = mutableListOf()
            override fun loadForRequest(url: HttpUrl): List<Cookie> = cookieStore

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore.addAll(cookies)
            }
        })
        okclient = builder.build()
        return okclient!!
    } else {
        return okclient!!
    }
}


suspend fun Call.await(): Response {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                continuation.resume(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                // Don't bother with resuming the continuation if it is already cancelled.
                if (continuation.isCancelled) return
                continuation.resumeWithException(e)
            }
        })

        continuation.invokeOnCancellation {
            try {
                cancel()
            } catch (ex: Throwable) {
                //Ignore cancel exception
            }
        }
    }
}