package es.borja.technicaltest.repositories

import es.borja.technicaltest.data.remote.PhotoDetailRemoteDataSource
import es.borja.technicaltest.models.PhotoDetail
import javax.inject.Inject

class PhotoDetailRepository @Inject constructor(
    private val remote: PhotoDetailRemoteDataSource
) {
    suspend fun getPhotoDetail(photoId: String, secret: String): PhotoDetail {
        return remote.getPhotoDetail(photoId = photoId, secret = secret)
    }
}