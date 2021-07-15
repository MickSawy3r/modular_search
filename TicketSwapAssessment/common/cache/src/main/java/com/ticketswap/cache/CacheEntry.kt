package com.ticketswap.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import okhttp3.Response
import java.util.Date
import java.util.Calendar

@Entity(tableName = "cache")
data class CacheEntry(
    @PrimaryKey val id: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "response") val entry: Response
)
