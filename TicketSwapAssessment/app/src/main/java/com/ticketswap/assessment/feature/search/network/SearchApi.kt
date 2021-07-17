package com.ticketswap.assessment.feature.search.network

import com.ticketswap.assessment.feature.search.datasource.network.ISpotifyApi
import com.ticketswap.assessment.feature.search.datasource.network.SearchResponse
import com.ticketswap.network.createObjectAdapter
import com.ticketswap.network.enqueue
import io.reactivex.rxjava3.core.Single
import okhttp3.HttpUrl
import okhttp3.Request

class SearchApi : ISpotifyApi {
    override fun searchSpotify(query: String): Single<SearchResponse> {
        val url = HttpUrl.Builder()

        url.addQueryParameter("q", query)
        url.addQueryParameter("type", "track,artist")

        return Request.Builder()
            .url(url.build())
            .build()
            .enqueue(
                responseAdapter = createObjectAdapter(),
                debug = true
            )
    }
}
