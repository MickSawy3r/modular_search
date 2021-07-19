package com.ticketswap.assessment.feature.search.datasource.network

import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SpotifyRemoteDataSource @Inject constructor(private val iSpotifyApi: ISpotifyApi) {

    fun search(query: String, authToken: String): Single<List<SpotifyDataModel>> {
        return iSpotifyApi.searchSpotify(query, authToken).map {
            it.toDomainModel()
        }
    }

    fun getArtistDetails(id: String, authToken: String): Single<SpotifyDataModel> {
        return iSpotifyApi.getArtistDetails(id, authToken).map {
            it.toDomainModel()
        }
    }

    fun getArtistAlbums(id: String, authToken: String): Single<SpotifyDataModel> {
        return iSpotifyApi.getArtistAlbums(id, authToken).map {
            it.toDomainModel()
        }
    }
}
