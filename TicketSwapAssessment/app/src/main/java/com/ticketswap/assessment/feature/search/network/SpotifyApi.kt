package com.ticketswap.assessment.feature.search.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SpotifyApi {
    @GET("search")
    fun searchSpotify(@Query("q") query: String, @Query("type") type: String): Single<SearchResponse>
}
