package com.ticketswap.assessment.feature.search.data

import com.ticketswap.assessment.feature.search.cache.CacheEntry
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel

fun SearchListItemDataModel.toCacheEntry(): CacheEntry {
    return CacheEntry(
        name = this.name,
        type = this.searchItemType,
        itemId = this.id
    )
}

fun List<SearchListItemDataModel>.toCacheEntryList(): List<CacheEntry> {
    return this.map {
        it.toCacheEntry()
    }
}
