package com.ticketswap.cache

import android.content.Context
import androidx.room.Room

object CacheLibrary {
    fun init(context: Context) {
        Room.databaseBuilder(
            context,
            CacheDatabase::class.java,
            "cache-db"
        ).build()
    }
}
