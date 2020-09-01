package app.androiddev.wallhaven.util

import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow


/**
 * The ViewModel acts upon these events accordingly by making API calls or saving/retrieving data in the database via the Repository layer.
 *
 * T should be the ViewState
 * U should be the UserIntents sealed class of what actions can be performed
 */
@ExperimentalCoroutinesApi
abstract class StateChannel<T, U>(
    private val initialState: T
) {

    // basic Channel<T> to listen to intents and state changes in the ViewModel
    val userIntentChannel = Channel<U>()
    protected val _state = MutableStateFlow(initialState)

    val state: StateFlow<T>
        get() = _state

    suspend fun handleIntents() {
        // ViewModel update the repository layer.
        // Use the Flow to consume the Channel values. -- ChannelFlow
        // https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/channel-flow.html
        userIntentChannel.consumeAsFlow().collect { userIntent ->
            // create a new viewState and set that to the value in the channel
            // Render of the MVI
            //TODO: add LCE<Result>(Loading/Content/Error)
            _state.value = reducer(userIntent, state.value)
        }
    }

    abstract suspend fun reducer(
        userIntent: U,
        currentState: T
    ): T
}

