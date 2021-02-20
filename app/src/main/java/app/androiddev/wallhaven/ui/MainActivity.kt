package app.androiddev.wallhaven.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import app.androiddev.wallhaven.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WallHavenApp(
                sharedPref = getSharedPreferences(
                    getString(R.string.sharedpref),
                    Context.MODE_PRIVATE
                )
            )
        }
    }
}

