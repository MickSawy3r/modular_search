package com.ticketswap.cache

import android.content.Context
import androidx.room.Room

object CacheLibrary {
    fun init(context: Context) {
        Room.inMemoryDatabaseBuilder(
            context,
            CacheDatabase::class.java
        ).build()
    }
}
