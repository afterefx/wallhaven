package app.androiddev.wallhaven.ui.toplist

sealed class TopListOperation {
    object GetGalleryPage : TopListOperation()
}

class TopListAction {
    fun action(op: TopListOperation, viewModel: TopListViewModel, page: Int = 1) {
        when (op) {
            TopListOperation.GetGalleryPage -> viewModel.userIntentChannel.offer(
                TopListUserIntent.GetPage(
                    page
                )
            )
        }
    }
}
