package es.borja.technicaltest.data.remote.models
import es.borja.technicaltest.models.Photo
import es.borja.technicaltest.models.Photos
import es.borja.technicaltest.models.SearchPhotos


data class SearchPhotosDto(
    val photos: PhotosDto,
    val stat: String,
)

data class PhotosDto(
    val page: Int,
    val pages: String,
    val perpage: Int,
    val total: String,
    val photo: List<PhotoDto>,
)

data class PhotoDto(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int,
)

fun SearchPhotosDto.toDomainModel() =
    SearchPhotos(
        photos = this.photos.toDomainModel(),
        stat = this.stat,
    )

fun PhotosDto.toDomainModel() =
    Photos(
        page = this.page,
        pages = this.pages.toInt(),
        perPage = this.perpage,
        total = this.total.toInt(),
        photo = this.photo.map {
            it.toDomainModel()
        }

    )

fun PhotoDto.toDomainModel() =
    Photo(
        id = this.id,
        owner = this.owner,
        secret = this.secret,
        server = this.server,
        farm = this.farm,
        title = this.title,
        thumbnail = "https://farm${this.farm}.staticflickr.com/${this.server}/${this.id}_${this.secret}.jpg"
    )
