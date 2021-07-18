package com.ticketswap.assessment.feature.search.network

import com.ticketswap.assessment.BuildConfig
import com.ticketswap.assessment.feature.search.datasource.network.ArtistDetailsResponse
import com.ticketswap.assessment.feature.search.datasource.network.ISpotifyApi
import com.ticketswap.assessment.feature.search.datasource.network.SearchResponse
import com.ticketswap.assessment.feature.search.datasource.network.TrackDetailsResponse
import com.ticketswap.network.createObjectAdapter
import com.ticketswap.network.enqueue
import io.reactivex.rxjava3.core.Single
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request

class SearchApi : ISpotifyApi {
    override fun searchSpotify(query: String, authToken: String): Single<SearchResponse> {
        val spotifyUrl = ("${BuildConfig.SPOTIFY_BASE_URL}search?q=$query&type=track,artist").toHttpUrl()

        return Request.Builder()
            .url(spotifyUrl)
            .addHeader("Authorization", authToken)
            .build()
            .enqueue(
                responseAdapter = createObjectAdapter(),
                debug = true
            )
    }

    override fun getTrackDetails(id: String, authToken: String): Single<TrackDetailsResponse> {
        return Request.Builder()
            .url("${BuildConfig.SPOTIFY_BASE_URL}tracks/$id")
            .addHeader("Authorization", authToken)
            .build()
            .enqueue(
                responseAdapter = createObjectAdapter(),
                debug = true
            )
    }

    override fun getArtistDetails(id: String, authToken: String): Single<ArtistDetailsResponse> {
        return Request.Builder()
            .url("${BuildConfig.SPOTIFY_BASE_URL}artists/$id")
            .addHeader("Authorization", authToken)
            .build()
            .enqueue(
                responseAdapter = createObjectAdapter(),
                debug = true
            )
    }
}
