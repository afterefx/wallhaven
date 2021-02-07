package app.androiddev.wallhaven.ui

sealed class ScreenState {
    object Latest : ScreenState()
    object TopList : ScreenState()
    object Random : ScreenState()
    object Detail : ScreenState()
}
