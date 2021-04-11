package app.androiddev.wallhaven.ui.appcontainer

import androidx.compose.foundation.lazy.LazyListState
import app.androiddev.wallhaven.ui.Screen

sealed class AppVmOperation {
    object Back : AppVmOperation()
    object Loading : AppVmOperation()
    class ChangeScreen(val screen: Screen) : AppVmOperation()
    class InitializeListStates(
        val latest: LazyListState,
        val top: LazyListState,
        val random: LazyListState
    ) : AppVmOperation()
}

object AppAction {
    fun action(
        op: AppVmOperation,
        appContainerViewModel: AppContainerViewModel,
    ) {
        val userIntentChannel = appContainerViewModel.userIntentChannel
        when (op) {
            AppVmOperation.Back -> userIntentChannel.offer(
                AppUserIntent.PreviousScreen
            )
            AppVmOperation.Loading -> userIntentChannel.offer(
                AppUserIntent.Loading
            )
            is AppVmOperation.ChangeScreen -> userIntentChannel.offer(
                AppUserIntent.ChangeScreen(op.screen)
            )
            is AppVmOperation.InitializeListStates -> userIntentChannel.offer(
                AppUserIntent.InitializeListStates(op.latest, op.top, op.random)
            )
        }
    }
}
