package app.androiddev.wallhaven.ui.toplist

import androidx.hilt.lifecycle.ViewModelInject
import app.androiddev.wallhaven.ui.gallery.GalleryStateChannel
import app.androiddev.wallhaven.ui.gallery.GalleryViewModel

class TopListViewModel @ViewModelInject constructor(
    private val stateChannel: GalleryStateChannel
) : GalleryViewModel(stateChannel)