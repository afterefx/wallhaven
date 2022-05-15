package app.androiddev.wallhaven.ui.gallery

sealed class GalleryOperation {
    object GetLatestPage : GalleryOperation()
    object GetTopListPage : GalleryOperation()
    object GetRandomListPage : GalleryOperation()
    object Loading : GalleryOperation()
}

class GalleryAction {
    fun action(
        op: GalleryOperation,
        vm: GalleryViewModel,
        page: Int = 1
    ) {
        when (op) {
            GalleryOperation.GetLatestPage -> vm.userIntentChannel.trySend(GalleryUserIntent.GetLatestPage(page)).isSuccess
            GalleryOperation.GetTopListPage -> vm.userIntentChannel.trySend(GalleryUserIntent.GetTopListPage(page)).isSuccess
            GalleryOperation.GetRandomListPage -> vm.userIntentChannel.trySend(GalleryUserIntent.GetRandomPage(page)).isSuccess
            GalleryOperation.Loading -> vm.userIntentChannel.trySend(GalleryUserIntent.Loading).isSuccess
        }
    }
}
