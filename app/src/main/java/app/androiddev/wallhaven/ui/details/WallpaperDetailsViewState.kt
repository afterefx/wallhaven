package app.androiddev.wallhaven.ui.details

import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import app.androiddev.wallhaven.ui.WallHavenRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import javax.inject.Inject

data class WallpaperDetailsViewState(val loading: Boolean = true, val wallpaperDetails: WallpaperDetails? = null)

/**
 * When the user interacts with the View, instances of Events are generated and
 * passed on to the ViewModel.  These Events are modelled as a sealed class.
 */
sealed class UserIntent {
    data class GetWallpaper(val id: String) : UserIntent()
}

/**
 * The ViewModel acts upon these events accordingly by making API calls or saving/retrieving data in the database via the Repository layer.
 */
@ExperimentalCoroutinesApi
class StateChannel @Inject constructor(
    private val repository: WallHavenRepository
) {

    // basic Channel<T> to listen to intents and state changes in the ViewModel
    val userIntentChannel = Channel<UserIntent>()
    private val _state = MutableStateFlow(WallpaperDetailsViewState())

    val state: StateFlow<WallpaperDetailsViewState>
        get() = _state

    suspend fun handleIntents() {
        // ViewModel update the repository layer.
        // Use the Flow to consume the Channel values. -- ChannelFlow
        // https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/channel-flow.html
        userIntentChannel.consumeAsFlow().collect { userIntent ->
            // create a new viewState and set that to the value in the channel
            // Render of the MVI
            //TODO: add LCE<Result>(Loading/Content/Error)
            _state.value = reduce(userIntent)
        }
    }

    /**
     * Takes old state and creates a new immutable state for the UI to render.
     */
    private suspend fun reduce(userIntent: UserIntent) = when (userIntent) {
        is UserIntent.GetWallpaper -> {
            _state.value.copy(loading = false, wallpaperDetails = repository.getWallPaperDetails(userIntent.id))
        }
    }
}