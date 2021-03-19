package app.androiddev.wallhaven.ui.appcontainer

import androidx.compose.foundation.lazy.LazyListState
import app.androiddev.wallhaven.ui.ScreenState
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import java.util.Stack

data class AppContainerViewState(
    val loading: Boolean = true,
    val currentScreen: ScreenState = ScreenState.Latest,
    val wallpaperDetailId: String? = null,
    val previousScreens: Stack<ScreenState>? = null,
    val latestListState: LazyListState? = null,
    val topListState: LazyListState? = null,
    val randomListState: LazyListState? = null,
) : MavericksState

class AppContainerViewModel(
    initialState: AppContainerViewState
) : MavericksViewModel<AppContainerViewState>(initialState) {

    fun navigate(screenState: ScreenState) = setState {
        val stack: Stack<ScreenState> = previousScreens ?: Stack()
        stack.add(currentScreen)
        copy(
            loading = false,
            currentScreen = screenState,
            previousScreens = stack
        )
    }

    fun showLoading() = setState {
        copy(loading = true)
    }

    fun initializeListStates(
        latest: LazyListState,
        top: LazyListState,
        random: LazyListState
    ) = setState {
        copy(
            latestListState = latest,
            topListState = top,
            randomListState = random
        )
    }

    fun goBack() = setState {
        val prevScreen: ScreenState = previousScreens?.pop() ?: ScreenState.Latest
        copy(
            currentScreen = prevScreen,
            wallpaperDetailId = null,
        )
    }
}