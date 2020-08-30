package app.androiddev.wallhaven.theme

import androidx.compose.animation.animate
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import app.androiddev.wallhaven.extensions.Color

val DarkColorPalette = darkColors(
    primary = Color("#232323"),
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryVariant = Color("#232323"),
    secondary = teal200
)


    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/

@Composable
fun WallHavenTheme(colors: Colors = DarkColorPalette, content: @Composable () -> Unit) {
    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

class ColorState(defaultColor: Color, defaultOnColor: Color) {
    var color: Color by mutableStateOf(defaultColor)
        private set
    var onColor: Color by mutableStateOf(defaultOnColor)
        private set

    suspend fun updateColors(newPrimary: Color, newOnPrimary: Color) {
        color = newPrimary
        onColor = newOnPrimary
    }
}

@Composable
fun DynamicTheme(colors: ColorState, content: @Composable () -> Unit) {
    val colors = MaterialTheme.colors.copy(
        primary = animate(colors.color, spring(stiffness = Spring.StiffnessLow)),
        onPrimary = animate(colors.onColor, spring(stiffness = Spring.StiffnessLow)),
        background = Color("#121212"),
        onBackground = Color.White
    )
    WallHavenTheme(colors = colors, content = content)
}
