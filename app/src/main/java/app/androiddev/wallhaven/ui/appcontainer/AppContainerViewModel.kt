package app.androiddev.wallhaven.ui.appcontainer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppContainerViewModel @Inject constructor(
    private val containerStateChannel: AppContainerStateChannel,
) : ViewModel() {

    //observe in view
    val state = containerStateChannel.state

    //send messages on this
    val userIntentChannel = containerStateChannel.userIntentChannel

    init {
        viewModelScope.launch {
            containerStateChannel.handleIntents()
        }
    }
}