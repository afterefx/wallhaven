package app.androiddev.wallhaven.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.androiddev.wallhaven.model.wallhavendata.SearchQuery
import app.androiddev.wallhaven.model.wallhavendata.SearchResults
import app.androiddev.wallhaven.ui.WallHavenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val wallHavenRepository: WallHavenRepository
) : ViewModel() {

    val _searchResults = MutableLiveData<SearchResults>()
    val searchResults: LiveData<SearchResults> = _searchResults

    fun search(text: String) {
        viewModelScope.launch {
            _searchResults.postValue(
                wallHavenRepository.search(SearchQuery())
            )
        }
    }
}