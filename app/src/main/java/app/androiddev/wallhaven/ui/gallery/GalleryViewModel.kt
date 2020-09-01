package app.androiddev.wallhaven.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class GalleryViewModel @ViewModelInject constructor(
    private val stateChannel: GalleryStateChannel
) : ViewModel() {

    val state = stateChannel.state
    val userIntentChannel = stateChannel.userIntentChannel

    init {
        viewModelScope.launch {
            stateChannel.handleIntents()
        }
    }
}

