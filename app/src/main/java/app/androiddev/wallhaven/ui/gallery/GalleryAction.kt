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
            GalleryOperation.GetLatestPage -> vm.userIntentChannel.offer(
                GalleryUserIntent.GetLatestPage(page)
            )
            GalleryOperation.GetTopListPage -> vm.userIntentChannel.offer(
                GalleryUserIntent.GetTopListPage(page)
            )
            GalleryOperation.GetRandomListPage -> vm.userIntentChannel.offer(
                GalleryUserIntent.GetRandomPage(page)
            )
            GalleryOperation.Loading -> vm.userIntentChannel.offer(
                GalleryUserIntent.Loading
            )
        }
    }
}
