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
                id = it.artists[0].id,
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

internal fun ArtistAlbumsResponse.toDomainModel(): SpotifyDataModel {
    return SpotifyDataModel(
        name = this.items[0].name,
        images = this.items.map { it.images[0].url },
        id = this.items[0].id,
        type = SearchItemType.TRACK
    )
}
