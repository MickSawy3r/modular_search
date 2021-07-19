package com.ticketswap.assessment.feature.search.datasource.local

import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchLocalDataSource @Inject constructor(val cache: ISearchCache) {
    fun saveCache(response: SpotifyDataModel): Completable {
        return cache.saveCache(
            CacheEntry(
                name = response.name,
                type = response.type,
                itemId = response.id,
                images = response.images
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
