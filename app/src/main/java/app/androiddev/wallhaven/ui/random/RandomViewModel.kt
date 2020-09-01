package app.androiddev.wallhaven.ui.random

import androidx.hilt.lifecycle.ViewModelInject
import app.androiddev.wallhaven.ui.gallery.GalleryStateChannel
import app.androiddev.wallhaven.ui.gallery.GalleryViewModel

class RandomViewModel @ViewModelInject constructor(stateChannel: GalleryStateChannel) :
    GalleryViewModel(stateChannel)