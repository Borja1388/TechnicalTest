package es.borja.technicaltest.data.remote.models

import es.borja.technicaltest.models.PhotoDetail


data class PhotoDetailDto(
    val photo: PhotoDetailInfoDto,
    val stat: String,

    )

data class PhotoDetailInfoDto(
    val id: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val dateuploaded: String,
    val isfavorite: Int,
    val license: Int,
    val safety_level: Int,
    val rotation: Int,
    val originalsecret: String,
    val originalformat: String,
    val owner: Owner,
    val title: Title,
    val description: Description,
    val visibility: Visibility,
    val dates: Dates,
    val views: Int,
    val editability: Editability,
    val publiceditability: Publiceditability,
    val usage: Usage,
    val comments: Comments,
    val urls: Urls,
    val media: String,
)

data class Owner(
    val nsid: String,
    val username: String,
    val realname: String,
    val location: String,
    val iconserver: String,
    val iconfarm: Int,
    val path_alias: String,
)

data class Title(
    val _content: String,
)

data class Description(
    val _content: String,
)

data class Visibility(
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int,
)

data class Dates(
    val posted: String,
    val taken: String,
    val takengranularity: Int,
    val takenunknown: Int,
    val lastupdate: String,
)

data class Editability(
    val cancomment: Int,
    val canaddmeta: Int,
)

data class Publiceditability(
    val cancomment: Int,
    val canaddmeta: Int,
)

data class Usage(
    val candownload: Int,
    val canblog: Int,
    val canshare: Int,
)

data class Comments(
    val _content: Int,
)

data class Urls(
    val url: List<Url>
)

data class Url(
    val type: String,
    val _content: String,
)

fun PhotoDetailDto.toDomainModel() =
    PhotoDetail(
        id = this.photo.id,
        secret = this.photo.secret,
        title = this.photo.title._content,
        description = this.photo.description._content,
        url = this.photo.urls.url.firstOrNull()?._content ?: "",
        owner = this.photo.owner.username,
        date = this.photo.dates.taken,

        )



