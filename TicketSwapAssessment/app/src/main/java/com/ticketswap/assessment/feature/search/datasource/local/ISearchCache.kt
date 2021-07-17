package com.ticketswap.assessment.feature.search.datasource.local

import com.ticketswap.assessment.feature.search.cache.CacheEntry
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface ISearchCache {
    fun saveCache(response: CacheEntry): Completable

    fun saveCacheList(response: List<CacheEntry>): Completable

    fun getLastCachedRequest(): Observable<CacheEntry>

    fun getCachedRequests(): Observable<List<CacheEntry>>
}
