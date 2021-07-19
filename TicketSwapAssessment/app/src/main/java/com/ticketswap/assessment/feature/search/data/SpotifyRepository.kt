package com.ticketswap.assessment.feature.search.data

import com.ticketswap.assessment.feature.search.datasource.local.SearchLocalDataSource
import com.ticketswap.assessment.feature.search.datasource.network.SpotifyRemoteDataSource
import com.ticketswap.assessment.feature.search.domain.datamodel.ArtistDetailsDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.TrackDetailsDataModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SpotifyRepository @Inject constructor(
    private val spotifyService: SpotifyRemoteDataSource,
    private val cache: SearchLocalDataSource
) {
    fun search(query: String, token: String): Single<List<SpotifyDataModel>> {
        return spotifyService.search(query, token)
    }

    fun getCached(): Single<List<SpotifyDataModel>> {
        return cache.getCachedRequests()
    }

    fun getArtistDetails(id: String, token: String): Single<ArtistDetailsDataModel> {
        return spotifyService.getArtistDetails(id, token)
            .flatMap {
                cache.saveCache(it).andThen(Single.just(it))
            }
    }

    fun getTrackDetails(id: String, token: String): Single<TrackDetailsDataModel> {
        return spotifyService.getTrackDetails(id, token)
            .flatMap {
                cache.saveCache(it).andThen(Single.just(it))
            }
    }

    companion object {
        private const val TAG = "SpotifyRepository"
    }
}
