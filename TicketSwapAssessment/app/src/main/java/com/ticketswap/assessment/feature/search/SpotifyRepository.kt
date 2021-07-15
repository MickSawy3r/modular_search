package com.ticketswap.assessment.feature.search

import com.ticketswap.assessment.feature.search.datamodel.SearchListItemDataModel
import com.ticketswap.assessment.feature.search.network.SpotifyService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SpotifyRepository @Inject constructor(private val spotifyService: SpotifyService) {
    fun search(query: String): Single<List<SearchListItemDataModel>> {
        return spotifyService
            .searchSpotify(query, "track,artist")
            .map { result ->
                val arrayList = mutableListOf<SearchListItemDataModel>()
                arrayList.addAll(result.artists.items.map { SearchListItemDataModel(name = "Artist: ${it.name}") })
                arrayList.addAll(result.tracks.items.map { SearchListItemDataModel(name = "Track: ${it.name}") })
                return@map arrayList
            }
    }
}
