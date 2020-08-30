package app.androiddev.wallhaven.ui.latest

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.androiddev.wallhaven.model.wallhavendata.SearchResults
import app.androiddev.wallhaven.ui.WallHavenRepository
import kotlinx.coroutines.launch

class LatestWallPapersViewModel @ViewModelInject constructor(
    private val latestStateChannel: LatestStateChannel
) : ViewModel() {

    val state = latestStateChannel.state
    val userIntentChannel = latestStateChannel.userIntentChannel

    init {
        viewModelScope.launch {
            latestStateChannel.handleIntents()
        }
    }
}

