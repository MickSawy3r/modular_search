package com.ticketswap.assessment.feature.search.data

import com.ticketswap.assessment.feature.search.cache.toDomainModel
import com.ticketswap.assessment.feature.search.datasource.local.ISearchCache
import com.ticketswap.assessment.feature.search.datasource.network.ISpotifyRemoteDataSource
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SpotifyRepository @Inject constructor(
    private val spotifyService: ISpotifyRemoteDataSource,
    private val cache: ISearchCache
) {
    fun search(query: String): Observable<List<SearchListItemDataModel>> {
        return Observable.fromSingle(
            spotifyService.search(query)
        ).doOnNext { searchResponse ->
            cache.saveCacheList(
                searchResponse.toCacheEntryList()
            )
        }
    }

    fun getLastSearch(): Observable<SearchListItemDataModel> {
        return cache.getLastCachedRequest()
            .map {
                it.toDomainModel()
            }
    }
}
