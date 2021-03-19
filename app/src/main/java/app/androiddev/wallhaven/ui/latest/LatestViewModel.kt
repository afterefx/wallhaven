package app.androiddev.wallhaven.ui.latest

import androidx.hilt.lifecycle.ViewModelInject
import app.androiddev.wallhaven.ui.gallery.GalleryStateChannel
import app.androiddev.wallhaven.ui.gallery.GalleryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LatestViewModel @Inject constructor(
    stateChannel: GalleryStateChannel
) : GalleryViewModel(stateChannel)