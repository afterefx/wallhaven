package app.androiddev.wallhaven.ui.details

import app.androiddev.wallhaven.model.wallhavendata.WallpaperDetails
import app.androiddev.wallhaven.ui.WallHavenRepository
import app.androiddev.wallhaven.util.StateChannel
import javax.inject.Inject

data class WallpaperDetailsViewState(
    val loading: Boolean = true,
    val wallpaperDetails: WallpaperDetails? = null
)

/**
 * When the user interacts with the View, instances of Events are generated and
 * passed on to the ViewModel.  These Events are modelled as a sealed class.
 */
sealed class DetailsUserIntent {
    data class GetWallpaper(val id: String) : DetailsUserIntent()
}

/**
 * The ViewModel acts upon these events accordingly by making API calls or saving/retrieving data in the database via the Repository layer.
 */
class DetailsStateChannel @Inject constructor(
    private val repository: WallHavenRepository
) : StateChannel<WallpaperDetailsViewState, DetailsUserIntent>(WallpaperDetailsViewState()) {

    override suspend fun reducer(
        userIntent: DetailsUserIntent,
    ): WallpaperDetailsViewState {
        when (userIntent) {
            is DetailsUserIntent.GetWallpaper -> {
                return _state.value.copy(
                    loading = false,
                    wallpaperDetails = repository.getWallPaperDetails(userIntent.id)
                )
            }
        }

    }
}