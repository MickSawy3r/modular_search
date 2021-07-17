package com.ticketswap.assessment.feature.search.cache

import androidx.room.Dao
import androidx.room.Query
import com.ticketswap.cache.BaseCacheDao
import io.reactivex.rxjava3.core.Observable
import java.util.Date

@Dao
abstract class CacheDao : BaseCacheDao<CacheEntry> {
    @Query("SELECT * FROM cache")
    abstract fun getCache(): Observable<List<CacheEntry>>

    @Query("SELECT * FROM cache where id = :date")
    abstract fun getCacheAtDate(date: Date): Observable<List<CacheEntry>>

    @Query("SELECT * FROM cache ORDER BY id DESC LIMIT 1")
    abstract fun getLatestCache(): Observable<CacheEntry>
}
