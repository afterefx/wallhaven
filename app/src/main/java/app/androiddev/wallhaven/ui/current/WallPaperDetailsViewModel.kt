package app.androiddev.wallhaven.ui.current

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import kotlinx.coroutines.launch

class WallPaperDetailsViewModel @ViewModelInject constructor(
    private val wallHavenRepository: WallHavenRepository
) : ViewModel() {

    var _wallpaperDetails = MutableLiveData<WallpaperDetails>()
    var wallpaperDetails: LiveData<WallpaperDetails> = _wallpaperDetails

    init {
        getWallpaperDetails("dgrgql")
    }

    fun getWallpaperDetails(id: String) {
        viewModelScope.launch {
            _wallpaperDetails.postValue(
                wallHavenRepository.getWallPaperDetails(id)
            )
        }
    }

}