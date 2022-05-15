package app.androiddev.wallhaven.ui.details

sealed class DetailMVOperation {
    class GetWallpaper(val id: String) : DetailMVOperation()
    object Loading : DetailMVOperation()
}

class DetailAction {
    fun action(
        op: DetailMVOperation,
        detailsViewModel: WallPaperDetailsViewModel,
    ) =
        when (op) {
            is DetailMVOperation.GetWallpaper -> detailsViewModel.userIntentChannel.trySend(
                DetailsUserIntent.GetWallpaper(op.id)
            ).isSuccess
            DetailMVOperation.Loading -> detailsViewModel.userIntentChannel.trySend(
                DetailsUserIntent.Loading
            ).isSuccess
        }
}
