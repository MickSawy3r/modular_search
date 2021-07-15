package com.ticketswap.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ticketswap.cache.converter.CacheConverters

@Database(
    entities = [CacheEntry::class],
    version = 1
)
@TypeConverters(CacheConverters::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}
