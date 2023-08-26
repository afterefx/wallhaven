package app.androiddev.wallhaven.ui.appcontainer

import androidx.compose.foundation.lazy.grid.LazyGridState
import app.androiddev.wallhaven.ui.Screen

sealed class AppVmOperation {
    object Back : AppVmOperation()
    object Loading : AppVmOperation()
    class ChangeScreen(val screen: Screen) : AppVmOperation()
    class InitializeListStates(
        val latest: LazyGridState,
        val top: LazyGridState,
        val random: LazyGridState
    ) : AppVmOperation()
    object Logout : AppVmOperation()
}

object AppAction {
    fun action(
        op: AppVmOperation,
        appContainerViewModel: AppContainerViewModel,
    ) {
        val userIntentChannel = appContainerViewModel.userIntentChannel
        when (op) {
            AppVmOperation.Back -> userIntentChannel.trySend(
                AppUserIntent.PreviousScreen
            ).isSuccess
            AppVmOperation.Loading -> userIntentChannel.trySend(
                AppUserIntent.Loading
            ).isSuccess
            is AppVmOperation.ChangeScreen -> userIntentChannel.trySend(
                AppUserIntent.ChangeScreen(op.screen)
            ).isSuccess
            is AppVmOperation.InitializeListStates -> userIntentChannel.trySend(
                AppUserIntent.InitializeListStates(op.latest, op.top, op.random)
            ).isSuccess
            AppVmOperation.Logout -> userIntentChannel.trySend(
                AppUserIntent.Logout
            ).isSuccess
        }
    }
}
