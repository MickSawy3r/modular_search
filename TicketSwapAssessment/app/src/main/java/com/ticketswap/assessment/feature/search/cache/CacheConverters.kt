package com.ticketswap.assessment.feature.search.cache

import androidx.room.TypeConverter
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchItemType
import java.util.Date

class CacheConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromSearchItemType(value: SearchItemType): Int {
        return value.ordinal
    }

    @TypeConverter
    fun toSearchItemType(ordinal: Int): SearchItemType {
        return SearchItemType.values()[ordinal]
    }

    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}
