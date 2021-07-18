package com.ticketswap.assessment.feature.search.cache

import androidx.room.Dao
import androidx.room.Query
import com.ticketswap.cache.BaseCacheDao
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.Date

@Dao
abstract class CacheDao : BaseCacheDao<CacheEntry> {
    @Query("SELECT * FROM cache ORDER BY date DESC")
    abstract fun getCache(): Single<List<CacheEntry>>

    @Query("SELECT * FROM cache ORDER BY id DESC LIMIT 1")
    abstract fun getLast(): Single<CacheEntry>

    @Query("SELECT * FROM cache where date = :date")
    abstract fun getCacheAtDate(date: Date): Single<List<CacheEntry>>

    @Query("SELECT * FROM cache WHERE date = (SELECT MAX(date) FROM cache)")
    abstract fun getLatestCache(): Single<List<CacheEntry>>
}
