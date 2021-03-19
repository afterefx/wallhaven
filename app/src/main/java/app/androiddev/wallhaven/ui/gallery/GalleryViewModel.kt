package app.androiddev.wallhaven.ui.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class GalleryViewModel @Inject constructor(
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

