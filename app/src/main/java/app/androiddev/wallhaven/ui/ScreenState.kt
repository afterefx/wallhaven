package app.androiddev.wallhaven.ui

sealed class ScreenState {
    object Latest : ScreenState()
    object TopList : ScreenState()
    object Random : ScreenState()
    class Detail(val id: String) : ScreenState()
}
