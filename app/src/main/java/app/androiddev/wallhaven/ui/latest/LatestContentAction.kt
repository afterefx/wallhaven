package app.androiddev.wallhaven.ui.latest

sealed class MVOperation {
    object GetGalleryPage : MVOperation()
}

class LatestContentAction {
    fun action(op: MVOperation, latestViewModel: LatestWallPapersViewModel, page: Int = 1) {
        when (op) {
            MVOperation.GetGalleryPage -> latestViewModel.userIntentChannel.offer(LatestUserIntent.GetPage(page))
        }
    }
}
