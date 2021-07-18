package com.ticketswap.assessment.feature.search.datasource.network


import com.squareup.moshi.Json

data class ArtistDetailsResponse(
    @Json(name = "external_urls")
    val externalUrls: ExternalUrls = ExternalUrls(),
    val followers: Followers = Followers(),
    val genres: List<String> = listOf(),
    val href: String = "",
    val id: String = "",
    val images: List<Image> = listOf(),
    val name: String = "",
    val popularity: Int = 0,
    val type: String = "",
    val uri: String = ""
) {
    data class ExternalUrls(
        val spotify: String = ""
    )

    data class Followers(
        val href: Any? = Any(),
        val total: Int = 0
    )

    data class Image(
        val height: Int = 0,
        val url: String = "",
        val width: Int = 0
    )
}