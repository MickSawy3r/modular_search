package com.ticketswap.assessment.feature.search.datasource.local

import com.ticketswap.assessment.feature.search.domain.datamodel.ArtistDetailsDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchItemType
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.TrackDetailsDataModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchLocalDataSource @Inject constructor(val cache: ISearchCache) {
    fun saveCache(response: ArtistDetailsDataModel): Completable {
        return cache.saveCache(
            CacheEntry(
                name = response.name,
                type = SearchItemType.ARTIST,
                itemId = response.id,
                image = response.image
            )
        )
    }

    fun saveCache(response: TrackDetailsDataModel): Completable {
        return cache.saveCache(
            CacheEntry(
                name = response.name,
                type = SearchItemType.TRACK,
                itemId = response.id,
                image = response.image
            )
        )
    }

    fun getCachedRequests(): Single<List<SpotifyDataModel>> {
        return cache.getCachedRequests()
            .map { cachedRequests ->
                cachedRequests.map {
                    it.toDomainModel()
                }
            }
    }

    fun getLastCachedRequest(): Single<SpotifyDataModel> {
        return cache.getLastCachedRequest()
            .map { it.toDomainModel() }
    }
}
