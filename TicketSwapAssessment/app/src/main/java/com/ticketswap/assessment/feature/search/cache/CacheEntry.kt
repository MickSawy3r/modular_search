package com.ticketswap.assessment.feature.search.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchItemType
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel
import java.util.Date
import java.util.Calendar

@Entity(tableName = "cache")
data class CacheEntry(
    @PrimaryKey val id: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: SearchItemType,
)

internal fun CacheEntry.toDomainModel() = SearchListItemDataModel(
    name = this.name,
    searchItemType = this.type
)
