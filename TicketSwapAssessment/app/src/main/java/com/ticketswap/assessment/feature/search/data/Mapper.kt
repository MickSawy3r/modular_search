package com.ticketswap.assessment.feature.search.data

import com.ticketswap.assessment.feature.search.datasource.local.CacheEntry
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel

fun SpotifyDataModel.toCacheEntry(): CacheEntry {
    return CacheEntry(
        name = this.name,
        type = this.type,
        itemId = this.id,
    )
}

fun List<SpotifyDataModel>.toCacheEntryList(): List<CacheEntry> {
    return this.map {
        it.toCacheEntry()
    }
}
