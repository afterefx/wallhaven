package app.androiddev.wallhaven.extensions

import androidx.compose.ui.graphics.Color

fun Color(colorString: String) = Color(android.graphics.Color.parseColor(colorString))
