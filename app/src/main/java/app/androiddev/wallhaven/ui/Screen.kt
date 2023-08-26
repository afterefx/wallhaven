package app.androiddev.wallhaven.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.Shuffle
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val navUp: Boolean = false
) {
    object Latest : Screen(route = "latest", title = "Latest", icon = Icons.Sharp.Home)
    object TopList : Screen(route = "toplist", title = "Top", icon = Icons.Sharp.Favorite)
    object Random : Screen(route = "random", title = "Random", icon = Icons.Sharp.Shuffle)
    object Detail : Screen(
        route = "detail/{id}", title = "Details", icon = Icons.Sharp.ArrowBack,
        navUp = true
    )
    object Profile : Screen(route = "profile", title = "Profile", icon = Icons.Sharp.Person)
}

fun getScreen(route: String?) =
    when (route) {
        Screen.Latest.route -> Screen.Latest
        Screen.TopList.route -> Screen.TopList
        Screen.Random.route -> Screen.Random
        Screen.Detail.route -> Screen.Detail
        Screen.Profile.route -> Screen.Profile
        else -> Screen.Latest
    }
