package com.ticketswap.assessment.feature.search.data

import com.ticketswap.assessment.feature.search.datasource.local.ISearchLocalDataSource
import com.ticketswap.assessment.feature.search.datasource.network.ISpotifyRemoteDataSource
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SpotifyRepository @Inject constructor(
    private val spotifyService: ISpotifyRemoteDataSource,
    private val cache: ISearchLocalDataSource
) {
    fun search(query: String, token: String): Single<List<SpotifyDataModel>> {
        return spotifyService.search(query, token)
    }

    fun getCached(): Single<List<SpotifyDataModel>> {
        return cache.getCachedRequests()
    }

    fun getArtistDetails(id: String, token: String): Single<SpotifyDataModel> {
        return spotifyService.getArtistDetails(id, token).flatMap {
            cache.saveCache(it).andThen(
                cache.getLastCachedRequest()
            )
        }
    }

    fun getTrackDetails(id: String, token: String): Single<SpotifyDataModel> {
        return spotifyService.getTrackDetails(id, token).flatMap {
            cache.saveCache(it).andThen(
                cache.getLastCachedRequest()
            )
        }
    }

    companion object {
        private const val TAG = "SpotifyRepository"
    }
}
