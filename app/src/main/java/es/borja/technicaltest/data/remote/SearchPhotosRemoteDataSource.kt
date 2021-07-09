package es.borja.technicaltest.data.remote

import es.borja.technicaltest.data.remote.models.toDomainModel
import es.borja.technicaltest.models.SearchPhotos
import javax.inject.Inject

class SearchPhotosRemoteDataSource @Inject constructor(private val flickrService: FlickrService) {
    suspend fun searchPhotos(text: String): SearchPhotos =
        flickrService.getSearchPhotos(text = text).toDomainModel()
}