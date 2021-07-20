package com.ticketswap.assessment.feature

import com.ticketswap.assessment.feature.search.data.SpotifyRepository
import com.ticketswap.assessment.feature.search.domain.failures.NullQueryFailure
import com.ticketswap.assessment.feature.search.domain.failures.SessionExpiredFailure
import com.ticketswap.assessment.feature.search.domain.usecase.LoadArtistDetailsUseCase
import com.ticketswap.authenticator.AuthGuard
import com.ticketswap.reactive.executor.PostExecutionThread
import com.ticketswap.reactive.executor.ThreadExecutor
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadArtistDetailsUseCaseTest {

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Mock
    lateinit var threadExecutor: ThreadExecutor

    @Mock
    lateinit var spotifyRepository: SpotifyRepository

    @Mock
    lateinit var authGuard: AuthGuard

    @Test
    fun testLoadArtistDetailsUseCase_useLoggedIn_shouldSuccess() {
        val token = "123"
        val artistId = "1111"

        val useCase = LoadArtistDetailsUseCase(
            postExecutionThread = postExecutionThread,
            threadExecutor = threadExecutor,
            spotifyRepository = spotifyRepository,
            authGuard = authGuard
        )
        `when`(authGuard.getAuthToken()).thenReturn(token)

        // Given I am logged in
        `when`(authGuard.userLoggedIn()).thenReturn(true)

        // And I requested a use case with params
        useCase.buildUseCaseSingle(artistId)

        // then the use case should request the token
        verify(authGuard, times(1))
            .getAuthToken()

        // And the use case should attach a token to the request
        verify(spotifyRepository, times(1))
            .getArtistDetails(artistId, token)
    }

    @Test
    fun testLoadArtistDetailsUseCase_userNotLoggedIn_shouldThrowUnauthorized() {
        val token = "123"
        val artistId = "1111"

        // Given I am not logged in
        `when`(authGuard.userLoggedIn()).thenReturn(false)

        // And I requested a use case with params
        val builtUseCase = LoadArtistDetailsUseCase(
            postExecutionThread = postExecutionThread,
            threadExecutor = threadExecutor,
            spotifyRepository = spotifyRepository,
            authGuard = authGuard
        ).buildUseCaseSingle(artistId)

        // then the use case should not request the token
        verify(authGuard, times(0))
            .getAuthToken()

        // And the use case should not attach a token to the request
        verify(spotifyRepository, times(0))
            .getArtistDetails(artistId, token)

        // And the use case should return Unauthorized Exception
        builtUseCase.test()
            .assertError { it is SessionExpiredFailure }
    }

    @Test
    fun testLoadArtistDetailsUseCase_noParams_shouldThrowNullQueryFailure() {
        // Given I requested a use case with no params
        val builtUseCase = LoadArtistDetailsUseCase(
            postExecutionThread = postExecutionThread,
            threadExecutor = threadExecutor,
            spotifyRepository = spotifyRepository,
            authGuard = authGuard
        ).buildUseCaseSingle()

        // then the use case should not request the token
        verify(authGuard, times(0))
            .getAuthToken()

        // And the use case should not attach a token to the request
        verify(spotifyRepository, times(0))
            .getArtistDetails(anyString(), anyString())

        // And the use case should return Unauthorized Exception
        builtUseCase.test().assertError {
            it is NullQueryFailure
        }
    }
}
