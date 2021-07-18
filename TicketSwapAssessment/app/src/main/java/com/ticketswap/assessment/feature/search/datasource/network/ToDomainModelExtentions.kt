package com.ticketswap.assessment.feature.search.datasource.network

import com.ticketswap.assessment.feature.search.domain.datamodel.ItemDetailsDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchItemType
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel

internal fun SearchResponse.toDomainModel(): List<SearchListItemDataModel> {
    val searchResult = mutableListOf<SearchListItemDataModel>()

    this.artists.items.forEach {
        searchResult.add(
            SearchListItemDataModel(
                name = it.name,
                searchItemType = SearchItemType.ARTIST
            )
        )
    }
    this.tracks.items.forEach {
        searchResult.add(
            SearchListItemDataModel(
                name = it.name,
                searchItemType = SearchItemType.TRACK
            )
        )
    }

    return searchResult
}

internal fun ArtistDetailsResponse.toDomainModel(): ItemDetailsDataModel {
    return ItemDetailsDataModel(
        name = this.name,
        images = this.images.map { it.url }
    )
}

internal fun TrackDetailsResponse.toDomainModel(): ItemDetailsDataModel {
    return ItemDetailsDataModel(
        name = this.name,
        images = this.album.images.map { it.url }
    )
}
