package com.ticketswap.assessment.feature.search.datasource.network

import com.ticketswap.assessment.feature.search.domain.datamodel.ItemDetailsDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ISpotifyRemoteDataSource @Inject constructor(private val iSpotifyApi: ISpotifyApi) {

    fun search(query: String): Single<List<SearchListItemDataModel>> {
        return iSpotifyApi.searchSpotify(query).map {
            it.toDomainModel()
        }
    }

    fun getArtistDetails(id: String, authToken: String): Single<ItemDetailsDataModel> {
        return iSpotifyApi.getArtistDetails(id, authToken).map {
            it.toDomainModel()
        }
    }

    fun getTrackDetails(id: String, authToken: String): Single<ItemDetailsDataModel> {
        return iSpotifyApi.getTrackDetails(id, authToken).map {
            it.toDomainModel()
        }
    }
}
