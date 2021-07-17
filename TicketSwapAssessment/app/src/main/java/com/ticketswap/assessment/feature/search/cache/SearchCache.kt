package com.ticketswap.assessment.feature.search.cache

import com.ticketswap.assessment.feature.search.datasource.local.ISearchCache
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class SearchCache constructor(private val cacheDao: CacheDao) : ISearchCache {
    override fun saveCache(response: CacheEntry): Completable = cacheDao.save(response)

    override fun saveCacheList(response: List<CacheEntry>): Completable = cacheDao.saveMany(response)

    override fun getLastCachedRequest(): Observable<CacheEntry> = cacheDao.getLatestCache()

    override fun getCachedRequests(): Observable<List<CacheEntry>> = cacheDao.getCache()
}
