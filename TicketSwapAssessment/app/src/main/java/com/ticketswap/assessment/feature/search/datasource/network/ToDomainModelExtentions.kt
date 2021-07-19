package com.ticketswap.assessment.feature.search.datasource.network

import com.ticketswap.assessment.feature.search.domain.datamodel.ArtistDetailsDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchItemType
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.TrackDetailsDataModel

internal fun SearchResponse.toDomainModel(): List<SpotifyDataModel> {
    val searchResult = mutableListOf<SpotifyDataModel>()

    this.artists.items.forEach {
        var image = ""
        if (it.images.isNotEmpty()) {
            image = it.images[0].url
        }
        searchResult.add(
            SpotifyDataModel(
                id = it.id,
                name = it.name,
                type = SearchItemType.ARTIST,
                image = image
            )
        )
    }
    this.tracks.items.forEach {
        var image = ""
        if (it.album.images.isNotEmpty()) {
            image = it.album.images[0].url
        }
        searchResult.add(
            SpotifyDataModel(
                id = it.id,
                name = it.name,
                type = SearchItemType.TRACK,
                image = image
            )
        )
    }

    return searchResult
}

fun ArtistDetailsResponse.toDomainModel(): ArtistDetailsDataModel {
    return ArtistDetailsDataModel(
        name = this.name,
        image = this.images[0].url,
        id = this.id,
        genres = this.genres,
        followers = this.followers.total
    )
}

fun TrackDetailsResponse.toDomainModel(): TrackDetailsDataModel {
    return TrackDetailsDataModel(
        id = this.id,
        artist = this.artists[0].name,
        image = this.album.images[0].url,
        year = this.album.releaseDate,
        name = this.name
    )
}
