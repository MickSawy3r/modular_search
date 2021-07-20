package com.ticketswap.assessment.feature.search.network

import com.ticketswap.assessment.feature.search.datasource.network.ArtistDetailsResponse
import com.ticketswap.assessment.feature.search.datasource.network.ISpotifyApi
import com.ticketswap.assessment.feature.search.datasource.network.SearchResponse
import com.ticketswap.assessment.feature.search.datasource.network.TrackDetailsResponse
import com.ticketswap.network.NetworkClient
import com.ticketswap.network.createObjectAdapter
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchApi @Inject constructor(private val networkClient: NetworkClient) : ISpotifyApi {
    override fun searchSpotify(query: String, authToken: String): Single<SearchResponse> {
        return networkClient.enqueue(
            responseAdapter = createObjectAdapter(),
            url = "search",
            queryParams = mapOf(
                "q" to query,
                "type" to "track,artist"
            ),
            headers = mapOf(
                "Authorization" to authToken
            )
        )
    }

    override fun getArtistDetails(id: String, authToken: String): Single<ArtistDetailsResponse> {
        return networkClient.enqueue(
            responseAdapter = createObjectAdapter(),
            url = "artists/$id",
            headers = mapOf(
                "Authorization" to authToken
            )
        )
    }

    override fun getTrackDetails(id: String, authToken: String): Single<TrackDetailsResponse> {
        return networkClient.enqueue(
            responseAdapter = createObjectAdapter(),
            url = "tracks/$id",
            headers = mapOf(
                "Authorization" to authToken
            ),
        )
    }
}
