package com.ticketswap.assessment.feature.search.datasource.local

import com.ticketswap.assessment.feature.search.cache.CacheEntry
import com.ticketswap.assessment.feature.search.cache.SearchCache
import com.ticketswap.assessment.feature.search.cache.toDomainModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ISearchLocalDataSource @Inject constructor(val cache: SearchCache) {
    fun saveCache(response: SpotifyDataModel): Completable {
        return cache.saveCache(
            CacheEntry(
                name = response.name,
                type = response.type,
                itemId = response.id
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
}
