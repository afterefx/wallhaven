package app.androiddev.wallhaven.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallPaperDetailsViewModel @Inject constructor(
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

