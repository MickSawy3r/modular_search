package com.ticketswap.assessment.feature.search.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ISearchCache {
    fun saveCache(cacheEntry: CacheEntry): Completable

    fun saveCacheList(cacheEntry: List<CacheEntry>): Completable

    fun getCachedRequests(): Single<List<CacheEntry>>

    fun getLastCachedRequest(): Single<CacheEntry>
}
