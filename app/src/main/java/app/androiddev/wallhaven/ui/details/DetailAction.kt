package app.androiddev.wallhaven.ui.details

sealed class DetailMVOperation {
    object GetWallpaper : DetailMVOperation()
    object Loading : DetailMVOperation()
}

class DetailAction {
    fun action(
        op: DetailMVOperation,
        detailsViewModel: WallPaperDetailsViewModel,
        id: String = ""
    ) =
        when (op) {
            DetailMVOperation.GetWallpaper -> detailsViewModel.userIntentChannel.offer(
                DetailsUserIntent.GetWallpaper(id)
            )
            DetailMVOperation.Loading -> detailsViewModel.userIntentChannel.offer(DetailsUserIntent.Loading)
        }
}
