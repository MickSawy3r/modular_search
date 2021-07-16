package com.ticketswap.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.util.Date

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCache(cacheEntry: CacheEntry): Completable

    @Query("SELECT * FROM cache")
    fun getCache(): Observable<List<CacheEntry>>

    @Query("SELECT * FROM cache where id = :date")
    fun getCacheAtDate(date: Date): Observable<List<CacheEntry>>

    @Query("SELECT * FROM cache ORDER BY id DESC LIMIT 1")
    fun getLatestCache(): Observable<CacheEntry>
}
