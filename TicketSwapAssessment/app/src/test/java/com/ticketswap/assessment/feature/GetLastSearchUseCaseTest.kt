package com.ticketswap.assessment.feature

import com.ticketswap.assessment.feature.search.data.SpotifyRepository
import com.ticketswap.assessment.feature.search.domain.usecase.GetLastSearchUseCase
import com.ticketswap.reactive.executor.PostExecutionThread
import com.ticketswap.reactive.executor.ThreadExecutor
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetLastSearchUseCaseTest {

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Mock
    lateinit var threadExecutor: ThreadExecutor

    @Mock
    lateinit var spotifyRepository: SpotifyRepository

    @Test
    fun testGetLastSearchUseCase() {
        GetLastSearchUseCase(
            postExecutionThread = postExecutionThread,
            threadExecutor = threadExecutor,
            spotifyRepository = spotifyRepository
        ).buildUseCaseSingle()

        verify(spotifyRepository, times(1))
            .getCached()
    }
}
