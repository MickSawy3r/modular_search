package com.ticketswap.assessment.feature.search.datasource.network

import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ISpotifyRemoteDataSource @Inject constructor(private val iSpotifyApi: ISpotifyApi) {

    fun search(query: String): Single<List<SearchListItemDataModel>> {
        return iSpotifyApi.searchSpotify(query).map {
            it.toDomainModel()
        }
    }
}
