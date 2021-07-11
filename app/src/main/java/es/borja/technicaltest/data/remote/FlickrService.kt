package es.borja.technicaltest.data.remote

import es.borja.technicaltest.data.remote.models.PhotoDetailDto
import es.borja.technicaltest.data.remote.models.SearchPhotosDto
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "866eea8d65e8072e2ef55cabf274fa14"

interface FlickrService {

    @GET("/services/rest/")
    suspend fun getSearchPhotos(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("text") text: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
    ): SearchPhotosDto

    @GET("/services/rest/")
    suspend fun getPhotoDetail(
        @Query("method") method: String = "flickr.photos.getInfo",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("photo_id") photoId: String,
        @Query("secret") secret: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
    ): PhotoDetailDto
}