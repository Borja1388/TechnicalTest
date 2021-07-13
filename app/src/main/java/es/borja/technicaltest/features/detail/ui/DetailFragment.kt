package es.borja.technicaltest.features.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.borja.technicaltest.databinding.FragmentDetailBinding
import es.borja.technicaltest.extensions.loadUrl
import es.borja.technicaltest.extensions.visibleIf
import es.borja.technicaltest.features.detail.ui.viewmodel.DetailViewModel
import es.borja.technicaltest.models.PhotoDetail
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.onEach {
            handleState(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.getPhotoDetail(args.photoId, args.secret)

    }

    private fun handleState(state: DetailViewModel.State) {
        binding.errorContainer.visibleIf(state is DetailViewModel.State.Error)
        when (state) {
            is DetailViewModel.State.Loading -> {/*no-op*/
            }
            is DetailViewModel.State.Error -> handleError(state.message)
            is DetailViewModel.State.Success -> handleSuccess(
                state.photoDetail
            )
        }
    }

    private fun handleSuccess(photoDetail: PhotoDetail) {
        binding.authorDetail.text = photoDetail.owner
        binding.dateDetail.text = photoDetail.date
        binding.imageDetail.loadUrl(args.thumbnail)
        binding.descriptionDetail.text = HtmlCompat.fromHtml(
            photoDetail.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

    }

    private fun handleError(message: Int) {
        binding.textError.text = getString(message)
    }
}