package com.ticketswap.assessment.feature.search.datasource.network


import com.squareup.moshi.Json

data class ArtistAlbumsResponse(
    val href: String = "",
    val items: List<Item> = listOf(),
    val limit: Int = 0,
    val next: String = "",
    val offset: Int = 0,
    val previous: String = "",
    val total: Int = 0
) {
    data class Item(
        @Json(name = "album_group")
        val albumGroup: String = "",
        @Json(name = "album_type")
        val albumType: String = "",
        val artists: List<Artist> = listOf(),
        @Json(name = "external_urls")
        val externalUrls: ExternalUrls = ExternalUrls(),
        val href: String = "",
        val id: String = "",
        val images: List<Image> = listOf(),
        val name: String = "",
        @Json(name = "release_date")
        val releaseDate: String = "",
        @Json(name = "release_date_precision")
        val releaseDatePrecision: String = "",
        @Json(name = "total_tracks")
        val totalTracks: Int = 0,
        val type: String = "",
        val uri: String = ""
    ) {
        data class Artist(
            @Json(name = "external_urls")
            val externalUrls: ExternalUrls = ExternalUrls(),
            val href: String = "",
            val id: String = "",
            val name: String = "",
            val type: String = "",
            val uri: String = ""
        ) {
            data class ExternalUrls(
                val spotify: String = ""
            )
        }

        data class ExternalUrls(
            val spotify: String = ""
        )

        data class Image(
            val height: Int = 0,
            val url: String = "",
            val width: Int = 0
        )
    }
}