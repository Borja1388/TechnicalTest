package es.borja.technicaltest.models


data class PhotoDetail(
    val id: String,
    val secret: String,
    val title: String,
    val description: String,
    val url: String,
    val owner: String,
    val date: String,
)


