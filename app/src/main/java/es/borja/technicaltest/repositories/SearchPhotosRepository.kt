package es.borja.technicaltest.repositories

import es.borja.technicaltest.data.remote.SearchPhotosRemoteDataSource
import es.borja.technicaltest.models.SearchPhotos
import javax.inject.Inject

class SearchPhotosRepository @Inject constructor(
    private val remote: SearchPhotosRemoteDataSource
) {
    suspend fun searchPhotos(text: String): SearchPhotos {
       return remote.searchPhotos(text = text)
    }
}