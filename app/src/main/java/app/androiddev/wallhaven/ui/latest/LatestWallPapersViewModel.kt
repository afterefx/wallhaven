package app.androiddev.wallhaven.ui.latest

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.androiddev.wallhaven.model.wallhavendata.SearchResults
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import app.androiddev.wallhaven.ui.WallHavenRepository
import kotlinx.coroutines.launch

class LatestWallPapersViewModel @ViewModelInject constructor(
    private val wallHavenRepository: WallHavenRepository
) : ViewModel() {

    val _latestWallpapers = MutableLiveData<SearchResults>()
    val latestWallpapers: LiveData<SearchResults> = _latestWallpapers

    init {
        getLatestWallpapers()
    }


    fun getLatestWallpapers() {
        viewModelScope.launch {
            _latestWallpapers.postValue(
                wallHavenRepository.getLatest()
            )
        }
    }



}

