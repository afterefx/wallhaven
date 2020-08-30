package app.androiddev.wallhaven.ui.toplist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TopListViewModel @ViewModelInject constructor(
    private val topListStateChannel: TopListStateChannel
) : ViewModel() {

    val state = topListStateChannel.state
    val userIntentChannel = topListStateChannel.userIntentChannel

    init {
        viewModelScope.launch {
            topListStateChannel.handleIntents()
        }
    }
}

