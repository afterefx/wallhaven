package app.androiddev.wallhaven.auth

import android.content.SharedPreferences
import javax.inject.Inject

private const val key = "API_KEY"

class ApiKeyController @Inject constructor(val sharedPref: SharedPreferences): IApiKeyController {
    override fun getApiKey(): String {
        return sharedPref.getString(key, "") ?: ""
    }

    override fun setApiKey(apiKey: String) {
        sharedPref.edit().putString(apiKey, key).apply()
    }

    override fun deleteApiKey() {
        sharedPref.edit().remove(key).apply()
    }
    override fun exists(): Boolean = getApiKey().isNotEmpty()
}

interface IApiKeyController {
    fun getApiKey(): String
    fun setApiKey(apiKey: String)

    fun deleteApiKey()

    fun exists(): Boolean
}