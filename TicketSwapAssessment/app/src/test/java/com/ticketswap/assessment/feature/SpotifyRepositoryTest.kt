package com.ticketswap.assessment.feature

import com.ticketswap.assessment.feature.search.data.SpotifyRepository
import com.ticketswap.assessment.feature.search.datasource.local.SearchLocalDataSource
import com.ticketswap.assessment.feature.search.datasource.network.SpotifyRemoteDataSource
import com.ticketswap.assessment.util.factories.ArtistDetailsFactory
import com.ticketswap.assessment.util.factories.SearchDataModelFactory
import com.ticketswap.assessment.util.factories.TrackDetailsFactory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SpotifyRepositoryTest {

    @Mock
    lateinit var spotifyService: SpotifyRemoteDataSource

    @Mock
    lateinit var cache: SearchLocalDataSource

    @Test
    fun testSearch() {
        val query = "Query"
        val token = "123"

        `when`(spotifyService.search(anyString(), anyString()))
            .thenReturn(Single.just(SearchDataModelFactory.generateArtistList(3)))

        // Given i request a search
        val response = SpotifyRepository(spotifyService, cache).search(query, token).test()

        // Then the repo should ask the service for result
        verify(spotifyService, times(1))
            .search(query, token)

        // And should get 3 artists
        response.assertValue { it.size == 3 }
    }

    @Test
    fun testGetCached() {
        `when`(cache.getCachedRequests())
            .thenReturn(Single.just(SearchDataModelFactory.generateArtistList(3)))

        // Given i request a search
        val response = SpotifyRepository(spotifyService, cache).getCached().test()

        // Then the repo should ask the service for result
        verify(cache, times(1))
            .getCachedRequests()

        // And should get 3 artists
        response.assertValue { it.size == 3 }
    }

    @Test
    fun testGetArtistDetails() {
        val mockArtist = ArtistDetailsFactory.generateArtistDetails()

        `when`(cache.saveCache(mockArtist))
            .thenReturn(Completable.complete())

        `when`(spotifyService.getArtistDetails(anyString(), anyString()))
            .thenReturn(Single.just(mockArtist))

        // Given i request an artist details
        val response = SpotifyRepository(spotifyService, cache)
            .getArtistDetails(mockArtist.id, mockArtist.name).test()

        // Then the repo should ask the service for result
        verify(spotifyService, times(1))
            .getArtistDetails(anyString(), anyString())

        // And it should cache the result summery
        verify(cache, times(1))
            .saveCache(mockArtist)

        // And I should get my artist :)
        response.assertValue { it.name == mockArtist.name }
    }

    @Test
    fun testGetTrackDetails() {
        val mockTrack = TrackDetailsFactory.generateTrackDetails()

        `when`(cache.saveCache(mockTrack))
            .thenReturn(Completable.complete())

        `when`(spotifyService.getTrackDetails(anyString(), anyString()))
            .thenReturn(Single.just(mockTrack))

        // Given i request an artist details
        val response = SpotifyRepository(spotifyService, cache)
            .getTrackDetails(mockTrack.id, mockTrack.name).test()

        // Then the repo should ask the service for result
        verify(spotifyService, times(1))
            .getTrackDetails(anyString(), anyString())

        // And it should cache the result summery
        verify(cache, times(1))
            .saveCache(mockTrack)

        // And I should get my track :)
        response.assertValue { it.name == mockTrack.name }
    }
}
