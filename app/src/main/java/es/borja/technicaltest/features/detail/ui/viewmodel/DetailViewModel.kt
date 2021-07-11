package es.borja.technicaltest.features.detail.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.borja.technicaltest.R
import es.borja.technicaltest.models.PhotoDetail
import es.borja.technicaltest.repositories.PhotoDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: PhotoDetailRepository) :
    ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State>
        get() = _state

    fun getPhotoDetail(photoId: String, secret: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val response = repository.getPhotoDetail(photoId, secret)
                _state.value = State.Success(response)
            } catch (e: Exception) {
                _state.value = State.Error(R.string.unknownError)
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val photoDetail: PhotoDetail) : State()
        data class Error(val message: Int) : State()
    }
}

