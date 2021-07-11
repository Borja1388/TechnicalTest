package es.borja.technicaltest.data.remote

import es.borja.technicaltest.data.remote.models.toDomainModel
import es.borja.technicaltest.models.PhotoDetail
import javax.inject.Inject

class PhotoDetailRemoteDataSource @Inject constructor(private val flickrService: FlickrService) {
    suspend fun getPhotoDetail(photoId: String, secret: String): PhotoDetail =
        flickrService.getPhotoDetail(photoId = photoId, secret = secret).toDomainModel()
}