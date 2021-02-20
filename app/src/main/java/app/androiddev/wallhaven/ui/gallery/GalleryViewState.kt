package app.androiddev.wallhaven.ui.gallery

import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import app.androiddev.wallhaven.ui.WallHavenRepository
import app.androiddev.wallhaven.util.StateChannel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

data class GalleryViewState(
    val initialized: Boolean = false,
    val loading: Boolean = true,
    val list: List<WallpaperDetails>? = null,
    val page: Int = 1
)

sealed class GalleryUserIntent {
    data class GetTopListPage(val page: Int) : GalleryUserIntent()
    data class GetLatestPage(val page: Int) : GalleryUserIntent()
    data class GetRandomPage(val page: Int) : GalleryUserIntent()
    object Loading : GalleryUserIntent()
}

/**
 * The ViewModel acts upon these events accordingly by making API calls or saving/retrieving data in the database via the Repository layer.
 */
class GalleryStateChannel @Inject constructor(private val repository: WallHavenRepository) :
    StateChannel<GalleryViewState, GalleryUserIntent>(
        GalleryViewState()
    ) {

    override suspend fun reducer(
        userIntent: GalleryUserIntent,
    ): GalleryViewState =
        when (userIntent) {
            is GalleryUserIntent.GetLatestPage -> {
                _state.value.copy(
                    initialized = true,
                    loading = false,
                    list = repository.getLatest(userIntent.page).data,
                    page = userIntent.page
                )
            }
            is GalleryUserIntent.GetTopListPage -> {
                _state.value.copy(
                    initialized = true,
                    loading = false,
                    list = repository.getTopList(userIntent.page).data,
                    page = userIntent.page
                )
            }
            is GalleryUserIntent.GetRandomPage -> {
                _state.value.copy(
                    initialized = true,
                    loading = false,
                    list = repository.getRandom().data,
                    page = userIntent.page
                )
            }
            is GalleryUserIntent.Loading -> {
                _state.value.copy(
                    initialized = true,
                    loading = true,
                    list = null
                )
            }
        }
}

