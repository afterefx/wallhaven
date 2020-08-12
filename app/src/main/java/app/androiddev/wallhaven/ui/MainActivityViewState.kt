package app.androiddev.wallhaven.ui

data class MainActivityViewState (var currentScreen: ScreenState)

sealed class ScreenState {
    object Latest : ScreenState()
    object Detail : ScreenState()
}
