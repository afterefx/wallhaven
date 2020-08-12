package app.androiddev.wallhaven.ui.details
class DetailFragment () {
}
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import androidx.compose.Recomposer
//import androidx.core.os.bundleOf
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.ui.core.setContent
//import app.androiddev.wallhaven.extensions.viewModelProviderFactoryOf
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import javax.inject.Inject
//
//@ExperimentalCoroutinesApi
//@AndroidEntryPoint
//class DetailFragment : Fragment() {
//
//
//    companion object {
//        private const val ARG_KEY_ID = "wallpaper_id"
//
//        @JvmStatic
//        fun create(id: String): DetailFragment {
//            return DetailFragment().apply {
//                arguments = bundleOf(ARG_KEY_ID to id)
//            }
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return FrameLayout(requireContext()).apply {
//            layoutParams =
//                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//
//            setContent(Recomposer.current()) {
//                wallpaperDetailsCompose.WallPaperDetailsContent(requireArguments().getString(ARG_KEY_ID, ""))
//            }
//        }
//    }
//}