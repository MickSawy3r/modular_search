package com.ticketswap.assessment.feature.search.datasource.local

import com.ticketswap.assessment.feature.search.domain.datamodel.ArtistDetailsDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.TrackDetailsDataModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchLocalDataSource @Inject constructor(val cache: ISearchCache) {
    fun saveCache(response: ArtistDetailsDataModel): Completable = cache.saveCache(
        response.toCacheEntry()
    )

    fun saveCache(response: TrackDetailsDataModel): Completable = cache.saveCache(
        response.toCacheEntry()
    )

    fun getCachedRequests(): Single<List<SpotifyDataModel>> = cache.getCachedRequests()
        .map { it.toDomainModel() }

    fun getLastCachedRequest(): Single<SpotifyDataModel> = cache.getLastCachedRequest()
        .map { it.toDomainModel() }
}
