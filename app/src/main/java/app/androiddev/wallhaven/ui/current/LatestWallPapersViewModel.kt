package app.androiddev.wallhaven.ui.current

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.androiddev.wallhaven.model.wallhavendata.SearchResults
import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
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

    fun gridWallPapers(wallpapersList: List<WallpaperDetails>): List<List<WallpaperDetails>> {
        val gridList: MutableList<List<WallpaperDetails>> = mutableListOf()

        var innerList: MutableList<WallpaperDetails> = mutableListOf()
        wallpapersList.forEachIndexed { index, wallpaperDetails ->
            if (index % 2 != 0 || index == 0) {
                innerList = mutableListOf()
                innerList.add(wallpaperDetails)
            } else {
                innerList.add(wallpaperDetails)
                gridList.add(innerList)
            }
        }

        return gridList
    }


}
