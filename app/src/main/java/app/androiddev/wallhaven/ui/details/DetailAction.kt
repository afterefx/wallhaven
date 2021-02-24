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
            is DetailMVOperation.GetWallpaper -> detailsViewModel.userIntentChannel.offer(
                DetailsUserIntent.GetWallpaper(op.id)
            )
            DetailMVOperation.Loading -> detailsViewModel.userIntentChannel.offer(DetailsUserIntent.Loading)
        }
}
