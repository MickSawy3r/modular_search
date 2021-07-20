package com.ticketswap.assessment.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ticketswap.assessment.feature.search.domain.usecase.GetLastSearchUseCase
import com.ticketswap.assessment.feature.search.domain.usecase.SearchSpotifyUseCase
import com.ticketswap.assessment.feature.search.presentation.SearchViewModel
import com.ticketswap.extention.Failure
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.amshove.kluent.`should be equal to`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @Mock
    lateinit var searchSpotifyUseCase: SearchSpotifyUseCase

    @Mock
    lateinit var getLastSearchUseCase: GetLastSearchUseCase

    @get:Rule
    val instantExecutionRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun fetchCache() {
        val viewModel = SearchViewModel(
            searchSpotifyUseCase,
            getLastSearchUseCase
        )

        viewModel.loading.observeForever { }

        viewModel.fetchCache()

        verify(getLastSearchUseCase, times(1))
            .execute(any(), anyOrNull())

        viewModel.loading.value `should be equal to` true
    }

    @Test
    fun search_NoInternet_ShouldEmitNoInternetFailure() {
        val viewModel = SearchViewModel(
            searchSpotifyUseCase,
            getLastSearchUseCase
        )

        viewModel.failure.observeForever { }

        // Given I have no internet Connection
        viewModel.setNetworkAvailable(false)

        // When I request a search
        viewModel.searchFor("Query")

        // Then I get a No Internet Failure
        viewModel.failure.value `should be equal to` Failure.NetworkConnection
    }

    @Test
    fun search_WithInternet_ShouldExecuteSearch() {
        val viewModel = SearchViewModel(
            searchSpotifyUseCase,
            getLastSearchUseCase
        )

        viewModel.loading.observeForever { }

        // Given I have a good internet Connection
        viewModel.setNetworkAvailable(true)

        // When I request a search
        viewModel.searchFor("Query")

        // Then Search Use Case should be triggered
        verify(searchSpotifyUseCase, times(1)).execute(any(), any())

        // And the application should be loading.
        viewModel.loading.value `should be equal to` true
    }

    @Test
    fun search_Reconnected_ShouldExecuteSearchOnLastQuery() {
        val viewModel = SearchViewModel(
            searchSpotifyUseCase,
            getLastSearchUseCase
        )

        viewModel.loading.observeForever { }
        viewModel.failure.observeForever { }

        // Given I have no internet Connection
        viewModel.setNetworkAvailable(false)

        // When I request a search
        viewModel.searchFor("Query")

        // Then I get a No Internet Failure
        assert(viewModel.failure.value == Failure.NetworkConnection)

        // When I reconnect
        viewModel.setNetworkAvailable(true)

        // Then I should get a loading indicator
        viewModel.loading.value `should be equal to` true

        // And Get Search Result should be requested from Spotify
        verify(searchSpotifyUseCase, times(1)).execute(any(), eq("Query"))
    }
}
