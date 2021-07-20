package com.ticketswap.assessment.feature.search.datasource.network

import com.ticketswap.assessment.feature.search.domain.datamodel.SearchItemType
import com.ticketswap.assessment.util.ResponseFactory
import com.ticketswap.extention.Failure
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tests SpotifyRemoteDataModel with it's Extensions.
 */

@RunWith(MockitoJUnitRunner::class)
class SpotifyRemoteDataModelTest {

    @Mock
    lateinit var spotifyApi: ISpotifyApi

    @Test
    fun testSearch_shouldSuccess() {
        val mockResponse = ResponseFactory.generateSearchResponse(
            artistLength = 3,
            trackLength = 2
        )
        val spotifyRemoteDataSource = SpotifyRemoteDataSource(spotifyApi)

        `when`(spotifyApi.searchSpotify(anyString(), anyString()))
            .thenReturn(
                Single.just(mockResponse)
            )

        // Given I request a search query
        val response = spotifyRemoteDataSource.search("Query", "Token").test()
        // Then I get a OK response
        response.assertValue { it.size == mockResponse.artists.items.size + mockResponse.tracks.items.size }
        // And the response contains 3 artists
        response.assertValue {
            it.filter { item -> item.type == SearchItemType.ARTIST }.size == mockResponse.artists.items.size
        }
        // And the response contains 2 tracks
        response.assertValue {
            it.filter { item -> item.type == SearchItemType.TRACK }.size == mockResponse.tracks.items.size
        }
        // And the artists contains Artist 01
        response.assertValue {
            it.filter { item ->
                item.type == SearchItemType.ARTIST
            }.filter { item ->
                item.name == "Artist 01"
            }.size == 1
        }
        // And the tracks contains Track 02
        response.assertValue {
            it.filter { item ->
                item.type == SearchItemType.TRACK
            }.filter { item ->
                item.name == "Track 02"
            }.size == 1
        }
    }

    @Test
    fun testSearch_shouldFailWithUnauthorizedException() {
        val spotifyRemoteDataSource = SpotifyRemoteDataSource(spotifyApi)

        `when`(spotifyApi.searchSpotify(anyString(), anyString()))
            .thenReturn(
                Single.error(Failure.UnauthorizedError)
            )

        // Given I request a search query
        val response = spotifyRemoteDataSource.search("Query", "Token").test()

        // Then I get a UnauthorizedError response
        response.assertError {
            it is Failure.UnauthorizedError
        }
    }

    @Test
    fun testGetArtistDetails_shouldSuccess() {
        // Given I requested artist details
        val id = "123"
        val mockResponse = ResponseFactory.generateArtistDetailsResponse(id)
        `when`(spotifyApi.getArtistDetails(anyString(), anyString()))
            .thenReturn(Single.just(mockResponse))

        // When I get the response
        val response = SpotifyRemoteDataSource(spotifyApi).getArtistDetails(
            id = id, authToken = "token"
        ).test()

        // Then I should get a successful response
        response.assertComplete()
        // And the response should contain the Artist Name
        response.assertValue { it.name == mockResponse.name }
        // And the response should contain the Artist ID
        response.assertValue { it.id == mockResponse.id }
        // And the response should contain the artist image
        response.assertValue { it.image == mockResponse.images[0].url }
        // And the response should contain the artist's Genre
        response.assertValue {
            it.genres == mockResponse.genres
        }
    }

    @Test
    fun testGetTrackDetails_shouldSuccess() {
        // Given I requested track details
        val id = "123"
        val mockResponse = ResponseFactory.generateTrackDetailsResponse(id)
        `when`(spotifyApi.getTrackDetails(anyString(), anyString()))
            .thenReturn(Single.just(mockResponse))

        // When I get the response
        val response = SpotifyRemoteDataSource(spotifyApi).getTrackDetails(
            id = id, authToken = "token"
        ).test()

        // Then I should get a successful response
        response.assertComplete()
        // And the response should contain the Track Name
        response.assertValue { it.name == mockResponse.name }
        // And the response should contain the Track ID
        response.assertValue { it.id == mockResponse.id }
        // And the response should contain the Track image
        response.assertValue { it.image == mockResponse.album.images[0].url }
        // And the response should contain the Track's Artist
        response.assertValue { it.artist == mockResponse.artists[0].name }
    }
}
