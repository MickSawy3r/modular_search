package com.ticketswap.assessment.feature.search.datasource.network

import com.ticketswap.assessment.feature.search.domain.datamodel.ArtistDetailsDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.TrackDetailsDataModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SpotifyRemoteDataSource @Inject constructor(private val iSpotifyApi: ISpotifyApi) {

    fun search(query: String, authToken: String): Single<List<SpotifyDataModel>> {
        return iSpotifyApi.searchSpotify(query, authToken).map {
            it.toDomainModel()
        }
    }

    fun getArtistDetails(id: String, authToken: String): Single<ArtistDetailsDataModel> {
        return iSpotifyApi.getArtistDetails(id, authToken).map {
            it.toDomainModel()
        }
    }

    fun getTrackDetails(id: String, authToken: String): Single<TrackDetailsDataModel> {
        return iSpotifyApi.getTrackDetails(id, authToken).map {
            it.toDomainModel()
        }
    }
}
