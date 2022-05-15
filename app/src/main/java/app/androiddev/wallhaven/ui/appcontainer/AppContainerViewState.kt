package app.androiddev.wallhaven.ui.appcontainer

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import app.androiddev.wallhaven.ui.Screen
import app.androiddev.wallhaven.ui.WallHavenRepository
import app.androiddev.wallhaven.util.StateChannel
import java.util.*
import javax.inject.Inject

data class AppContainerViewState(
    val loading: Boolean = true,
    val currentScreen: Screen = Screen.Latest,
    val wallpaperDetailId: String? = null,
    val previousScreens: Stack<Screen>? = null,
    val latestListState: LazyGridState? = null,
    val topListState: LazyGridState? = null,
    val randomListState: LazyGridState? = null,
)

/**
 * When the user interacts with the View, instances of Events are generated and
 * passed on to the ViewModel.  These Events are modelled as a sealed class.
 */
sealed class AppUserIntent {
    data class ChangeScreen(val screen: Screen) : AppUserIntent()
    object Loading : AppUserIntent()
    object PreviousScreen : AppUserIntent()
    class InitializeListStates(
        val latest: LazyGridState,
        val top: LazyGridState,
        val random: LazyGridState
    ) : AppUserIntent()
}

/**
 * The ViewModel acts upon these events accordingly by making API calls or saving/retrieving data in the database via the Repository layer.
 */
class AppContainerStateChannel @Inject constructor(
    private val repository: WallHavenRepository
) : StateChannel<AppContainerViewState, AppUserIntent>(AppContainerViewState()) {

    override suspend fun reducer(
        userIntent: AppUserIntent,
    ): AppContainerViewState =
        when (userIntent) {
            is AppUserIntent.ChangeScreen -> {
                val stack: Stack<Screen> = _state.value.previousScreens ?: Stack()
                stack.add(_state.value.currentScreen)
                _state.value.copy(
                    loading = false,
                    currentScreen = userIntent.screen,
                    previousScreens = stack
                )
            }
            is AppUserIntent.Loading -> {
                _state.value.copy(
                    loading = true,
                )
            }
            is AppUserIntent.InitializeListStates -> {
                _state.value.copy(
                    latestListState = userIntent.latest,
                    topListState = userIntent.top,
                    randomListState = userIntent.random
                )
            }
            AppUserIntent.PreviousScreen -> {
                val prevScreen: Screen =
                    _state.value.previousScreens?.pop() ?: Screen.Latest
                _state.value.copy(
                    currentScreen = prevScreen,
                    wallpaperDetailId = null,
                )
            }
        }
}