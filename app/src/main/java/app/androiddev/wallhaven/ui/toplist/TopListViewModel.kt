package app.androiddev.wallhaven.ui.toplist

import androidx.hilt.lifecycle.ViewModelInject
import app.androiddev.wallhaven.ui.gallery.GalleryStateChannel
import app.androiddev.wallhaven.ui.gallery.GalleryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopListViewModel @Inject constructor(
    private val stateChannel: GalleryStateChannel
) : GalleryViewModel(stateChannel)