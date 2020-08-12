package app.androiddev.wallhaven.extensions

import androidx.ui.graphics.Color

fun Color(colorString: String): Color {
    return Color(android.graphics.Color.parseColor("$colorString"))
}