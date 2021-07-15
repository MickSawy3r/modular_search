package com.ticketswap.assessment.feature.search.network

import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyService @Inject constructor(retrofit: Retrofit) : SpotifyApi {
    private val spotifyApi by lazy {
        retrofit.create(SpotifyApi::class.java)
    }

    override fun searchSpotify(query: String, type: String): Single<SearchResponse> =
        spotifyApi.searchSpotify(query, type)
}
