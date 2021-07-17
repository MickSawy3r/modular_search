package com.ticketswap.assessment.feature.search.datasource.local

import com.ticketswap.assessment.feature.search.cache.CacheEntry
import com.ticketswap.assessment.feature.search.cache.SearchCache
import com.ticketswap.assessment.feature.search.cache.toDomainModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ISearchLocalDataSource @Inject constructor(val cache: SearchCache) {
    fun saveCache(response: SearchListItemDataModel): Completable {
        return cache.saveCache(
            CacheEntry(
                name = response.name,
                type = response.searchItemType
            )
        )
    }

    fun getLastCachedRequest(): Observable<SearchListItemDataModel> {
        return cache.getLastCachedRequest()
            .map { it.toDomainModel() }
    }

    fun getCachedRequests(): Observable<List<SearchListItemDataModel>> {
        return cache.getCachedRequests()
            .map { cachedRequests ->
                cachedRequests.map {
                    it.toDomainModel()
                }
            }
    }
}
