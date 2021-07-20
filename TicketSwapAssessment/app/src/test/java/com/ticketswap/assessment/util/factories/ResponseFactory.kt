package com.ticketswap.assessment.util.factories

import com.ticketswap.assessment.feature.search.datasource.network.ArtistDetailsResponse
import com.ticketswap.assessment.feature.search.datasource.network.SearchResponse
import com.ticketswap.assessment.feature.search.datasource.network.TrackDetailsResponse

object ResponseFactory {
    /**
     * @return SearchResponse
     * values:
     *      ID, Name,       Type,   Image
     *      1,  Artist 01,  artist, https://google.com
     *      1,  Track 01,   track,  https://google.com
     */
    fun generateSearchResponse(artistLength: Int = 3, trackLength: Int = 2): SearchResponse {
        val artists = mutableListOf<SearchResponse.Artists.Item>()
        for (i in 1..artistLength) {
            artists.add(
                SearchResponse.Artists.Item(
                    id = String.format("%02d", i),
                    name = "Artist ${String.format("%02d", i)}",
                    type = "artist",
                    popularity = 123,
                    images = listOf(
                        SearchResponse.Artists.Item.Image(
                            url = "https://google.com"
                        )
                    )
                )
            )
        }

        val tracks = mutableListOf<SearchResponse.Tracks.Item>()
        for (i in 1..trackLength) {
            tracks.add(
                SearchResponse.Tracks.Item(
                    id = String.format("%02d", i),
                    type = "track",
                    name = "Track ${String.format("%02d", i)}",
                    album = SearchResponse.Tracks.Item.Album(
                        images = listOf(
                            SearchResponse.Tracks.Item.Album.Image(
                                url = "https://google.com"
                            )
                        )
                    )
                )
            )
        }

        return SearchResponse(
            artists = SearchResponse.Artists(
                items = artists
            ),
            tracks = SearchResponse.Tracks(
                items = tracks
            )
        )
    }

    /**
     * @return ArtistDetailsResponse
     * values:
     *      name: Artist
     *      image = https://google.com
     *      id = 01
     *      genres = Pop, Rock
     *      followers = 123
     */
    fun generateArtistDetailsResponse(id: String = "01") = ArtistDetailsResponse(
        name = "Artist",
        images = listOf(ArtistDetailsResponse.Image(url = "https://google.com")),
        id = id,
        genres = listOf("Pop", "Rock"),
        followers = ArtistDetailsResponse.Followers(total = 123)
    )

    fun generateTrackDetailsResponse(id: String = "01") = TrackDetailsResponse(
        id = id,
        name = "Track",
        artists = listOf(
            TrackDetailsResponse.Artist(
                name = "Artist"
            )
        ),
        album = TrackDetailsResponse.Album(
            images = listOf(
                TrackDetailsResponse.Album.Image(
                    url = "https://google.com"
                )
            ),
            releaseDate = "01/01/2021"
        ),
    )
}
