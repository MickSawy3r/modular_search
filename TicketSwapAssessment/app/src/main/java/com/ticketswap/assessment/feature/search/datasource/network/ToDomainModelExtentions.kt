package com.ticketswap.assessment.feature.search.datasource.network

import com.ticketswap.assessment.feature.search.domain.datamodel.SearchItemType
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel

internal fun SearchResponse.toDomainModel(): List<SpotifyDataModel> {
    val searchResult = mutableListOf<SpotifyDataModel>()

    this.artists.items.forEach {
        searchResult.add(
            SpotifyDataModel(
                id = it.id,
                name = it.name,
                type = SearchItemType.ARTIST,
                images = it.images.map { image -> image.url }
            )
        )
    }
    this.tracks.items.forEach {
        searchResult.add(
            SpotifyDataModel(
                id = it.id,
                name = it.name,
                type = SearchItemType.TRACK,
                images = it.album.images.map { image -> image.url }
            )
        )
    }

    return searchResult
}

internal fun ArtistDetailsResponse.toDomainModel(): SpotifyDataModel {
    return SpotifyDataModel(
        name = this.name,
        images = this.images.map { it.url },
        id = this.id,
        type = SearchItemType.ARTIST
    )
}

internal fun TrackDetailsResponse.toDomainModel(): SpotifyDataModel {
    return SpotifyDataModel(
        name = this.name,
        images = this.album.images.map { it.url },
        id = this.id,
        type = SearchItemType.TRACK
    )
}
