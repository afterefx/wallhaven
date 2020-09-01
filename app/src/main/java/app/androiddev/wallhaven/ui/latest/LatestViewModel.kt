package app.androiddev.wallhaven.ui.latest

import androidx.hilt.lifecycle.ViewModelInject
import app.androiddev.wallhaven.ui.gallery.GalleryStateChannel
import app.androiddev.wallhaven.ui.gallery.GalleryViewModel

class LatestViewModel @ViewModelInject constructor(
    private val stateChannel: GalleryStateChannel
) : GalleryViewModel(stateChannel)