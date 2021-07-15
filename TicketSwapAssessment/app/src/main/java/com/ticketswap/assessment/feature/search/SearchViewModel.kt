package com.ticketswap.assessment.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.core.exception.Failure
import com.ticketswap.assessment.core.platform.BaseViewModel
import com.ticketswap.assessment.feature.search.datamodel.SearchListItemDataModel
import com.ticketswap.assessment.feature.search.usecase.SearchSpotifyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchSpotifyUseCase: SearchSpotifyUseCase
) : BaseViewModel() {

    private val _searchLiveData: MutableLiveData<List<SearchListItemDataModel>> = MutableLiveData()
    val searchLiveData: LiveData<List<SearchListItemDataModel>> = _searchLiveData

    private val _loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    fun search(query: String) {
        _loadingLiveData.postValue(true)
        searchSpotifyUseCase.execute(observer = SearchObserver(), params = query)
    }

    private inner class SearchObserver : DisposableSingleObserver<List<SearchListItemDataModel>>() {

        override fun onSuccess(t: List<SearchListItemDataModel>) {
            _loadingLiveData.postValue(false)
            _searchLiveData.postValue(t)
        }

        override fun onError(e: Throwable) {
            _loadingLiveData.postValue(false)
            handleFailure(Failure.NetworkConnection)
        }
    }
}
