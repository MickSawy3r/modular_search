package com.ticketswap.assessment.feature.search.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.extention.exception.Failure
import com.ticketswap.platform.core.BaseViewModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel
import com.ticketswap.assessment.feature.search.domain.usecase.GetLastSearchUseCase
import com.ticketswap.assessment.feature.search.domain.usecase.SearchSpotifyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchSpotifyUseCase: SearchSpotifyUseCase,
    private val getLastSearchUseCase: GetLastSearchUseCase
) : BaseViewModel() {

    private val _searchLiveData: MutableLiveData<List<SearchListItemDataModel>> = MutableLiveData()
    val searchLiveData: LiveData<List<SearchListItemDataModel>> = _searchLiveData

    fun start() {
        getLastSearchUseCase.execute(observer = SearchObserver())
    }

    fun search(query: String) {
        setLoading(true)
        searchSpotifyUseCase.execute(
            observer = SearchObserver(),
            params = query
        )
    }

    private inner class SearchObserver : DisposableObserver<List<SearchListItemDataModel>>() {

        override fun onError(e: Throwable) {
            setLoading(false)
            handleFailure(Failure.NetworkConnection)
        }

        override fun onNext(t: List<SearchListItemDataModel>?) {
            setLoading(false)
            _searchLiveData.postValue(t)
        }

        override fun onComplete() {
            Log.d(TAG, "onComplete: ")
        }
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}
