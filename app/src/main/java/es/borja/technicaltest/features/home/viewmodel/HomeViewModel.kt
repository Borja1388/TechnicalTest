package es.borja.technicaltest.features.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.borja.technicaltest.repositories.SearchPhotosRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: SearchPhotosRepository) : ViewModel(){
    fun searchPhotos(text: String){
        viewModelScope.launch {
            val response = repository.searchPhotos(text)
            Log.e("response", response.toString())
        }
    }
}