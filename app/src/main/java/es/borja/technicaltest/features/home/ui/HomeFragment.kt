package es.borja.technicaltest.features.home.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.borja.technicaltest.databinding.FragmentHomeBinding
import es.borja.technicaltest.extensions.showIf
import es.borja.technicaltest.extensions.visibleIf
import es.borja.technicaltest.features.home.viewmodel.HomeViewModel
import es.borja.technicaltest.models.SearchPhotos
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val viewmodel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerPhotos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPhotos.adapter = PhotosAdapter(mutableListOf()) {
            navigateToDetail(it.id, it.secret, it.thumbnail)
        }

        viewmodel.state.onEach {
            handleState(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.btnSearch.setOnClickListener {
            if (!binding.search.text.isNullOrEmpty()) {
                viewmodel.searchPhotos(binding.search.text.toString())
            }
        }
    }

    private fun handleState(state: HomeViewModel.State) {
        binding.errorContainer.visibleIf(state is HomeViewModel.State.Error || state is HomeViewModel.State.Empty)
        binding.loadingView.showIf(state is HomeViewModel.State.Loading)
        when (state) {
            is HomeViewModel.State.Error -> handleError(state.message)
            is HomeViewModel.State.Empty -> handleEmpty(state.message)
            is HomeViewModel.State.Success -> handleSuccess(
                state.searchPhotos
            )
        }
    }

    private fun handleSuccess(searchPhotos: SearchPhotos) {
        val recyclerViewState: Parcelable? =
            binding.recyclerPhotos.layoutManager?.onSaveInstanceState()
        (binding.recyclerPhotos.adapter as PhotosAdapter).updateData(searchPhotos.photos.photo)
        binding.recyclerPhotos.layoutManager?.onRestoreInstanceState(recyclerViewState)

    }

    private fun handleError(message: Int) {
        binding.textError.text = getString(message)
    }

    private fun handleEmpty(message: Int) {
        binding.textError.text = getString(message)
    }

    private fun navigateToDetail(photoId: String, secret: String, url: String) {
        val action = HomeFragmentDirections.homeFragmentToDetail(photoId, secret, url)
        findNavController().navigate(action)
    }
}