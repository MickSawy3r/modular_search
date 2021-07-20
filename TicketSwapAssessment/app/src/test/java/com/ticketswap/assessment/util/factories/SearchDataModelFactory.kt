package com.ticketswap.assessment.util.factories

import com.ticketswap.assessment.feature.search.domain.datamodel.SearchItemType
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel

object SearchDataModelFactory {

    fun generateArtistSpotifyDataModel(name: String = "Artist", id: String = "123") =
        SpotifyDataModel(
            name = name,
            type = SearchItemType.ARTIST,
            image = "https://google.com",
            id = id
        )

    fun generateTrackSpotifyDataModel(name: String = "Artist", id: String = "123") =
        SpotifyDataModel(
            name = name,
            type = SearchItemType.TRACK,
            image = "https://google.com",
            id = id
        )

    fun generateArtistList(artistsNum: Int = 3): List<SpotifyDataModel> {
        val artists = mutableListOf<SpotifyDataModel>()
        for (i in 1..artistsNum) {
            artists.add(generateArtistSpotifyDataModel(id = i.toString()))
        }
        return artists
    }

    fun generateTrackList(tracksNum: Int = 2): List<SpotifyDataModel> {
        val tracks = mutableListOf<SpotifyDataModel>()
        for (i in 1..tracksNum) {
            tracks.add(generateTrackSpotifyDataModel(id = i.toString()))
        }
        return tracks
    }
}
