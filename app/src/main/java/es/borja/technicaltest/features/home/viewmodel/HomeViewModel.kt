package es.borja.technicaltest.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.borja.technicaltest.R
import es.borja.technicaltest.models.SearchPhotos
import es.borja.technicaltest.repositories.SearchPhotosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: SearchPhotosRepository) :
    ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Initial)
    val state: StateFlow<State>
        get() = _state

    fun searchPhotos(text: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val response = repository.searchPhotos(text)
                if (response.photos.photo.isEmpty()) {
                    _state.value = State.Empty(R.string.noResults)
                } else {
                    _state.value = State.Success(response)
                }

            } catch (e: Exception) {
                _state.value = State.Error(R.string.unknownError)
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val searchPhotos: SearchPhotos) : State()
        data class Error(val message: Int) : State()
        data class Empty(val message: Int) : State()
        object Initial : State()
    }
}