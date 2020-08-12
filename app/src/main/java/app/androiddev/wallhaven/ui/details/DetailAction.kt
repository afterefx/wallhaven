package app.androiddev.wallhaven.ui.details

import javax.inject.Inject

sealed class MVOperation {
    object GetWallpaper : MVOperation()
}

class DetailAction @Inject constructor() {
    fun action(op: MVOperation, detailsViewModel: WallPaperDetailsViewModel, id: String = "") {
        when (op) {
            MVOperation.GetWallpaper -> detailsViewModel.userIntentChannel.offer( UserIntent.GetWallpaper( id ) )
        }
    }
}
