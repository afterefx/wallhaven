package app.androiddev.wallhaven.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class WallPaperDetailsViewModel @ViewModelInject  constructor(
    private val detailsStateChannel: DetailsStateChannel,
) : ViewModel() {

    val state = detailsStateChannel.state
    val userIntentChannel = detailsStateChannel.userIntentChannel

    init {
//        getWallpaperDetails("dgrgql")
        viewModelScope.launch {
            detailsStateChannel.handleIntents()
        }
    }

/*
    fun getWallpaperDetails(id: String) {
        viewModelScope.launch {
            stateChannel.handleIntents()
            var _wallpaperDetails = MutableLiveData<WallpaperDetails>()
            var wallpaperDetails: LiveData<WallpaperDetails> = _wallpaperDetails
            _wallpaperDetails.postValue(
                wallHavenRepository.getWallPaperDetails(id)
            )
        }
    }
*/

}

