package com.ticketswap.assessment.feature.search.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.assessment.feature.search.domain.usecase.GetLastSearchUseCase
import com.ticketswap.assessment.feature.search.domain.usecase.SearchSpotifyUseCase
import com.ticketswap.extention.Failure
import com.ticketswap.network.UnauthorizedException
import com.ticketswap.platform.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchSpotifyUseCase: SearchSpotifyUseCase,
    private val getLastSearchUseCase: GetLastSearchUseCase
) : BaseViewModel() {

    private val _searchLiveData: MutableLiveData<List<SpotifyDataModel>> = MutableLiveData()
    val search: LiveData<List<SpotifyDataModel>> = _searchLiveData

    private var isConnected = false
    private var lastSearchQuery: String? = null

    fun start() {
        setLoading(true)
        getLastSearchUseCase.execute(
            observer = SearchObserver()
        )
    }

    fun search(query: String) {
        if (query.isEmpty()) {
            handleFailure(EmptySearchQueryFailure())
            return
        }

        lastSearchQuery = query
        if (isConnected) {
            setLoading(true)
            searchSpotifyUseCase.execute(
                observer = SearchObserver(),
                params = query
            )
        }
    }

    fun retry() {
        lastSearchQuery?.let { search(it) }
    }

    fun setNetworkAvailable(isConnected: Boolean) {
        this.isConnected = isConnected
    }

    private inner class SearchObserver : DisposableSingleObserver<List<SpotifyDataModel>>() {

        override fun onError(e: Throwable) {
            Log.d(TAG, "onError: $e at \n${e.stackTrace}")
            setLoading(false)
            if (e is UnauthorizedException) {
                handleFailure(Failure.UnauthorizedError)
            } else {
                handleFailure(Failure.NetworkConnection)
            }
        }

        override fun onSuccess(t: List<SpotifyDataModel>?) {
            _searchLiveData.postValue(t)
            setLoading(false)
        }
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}
