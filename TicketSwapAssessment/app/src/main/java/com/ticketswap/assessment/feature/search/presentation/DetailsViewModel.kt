package com.ticketswap.assessment.feature.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.feature.search.domain.datamodel.ItemDetailsDataModel
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchItemType
import com.ticketswap.assessment.feature.search.domain.failures.NullQueryFailure
import com.ticketswap.assessment.feature.search.domain.usecase.LoadArtistDetailsUseCase
import com.ticketswap.assessment.feature.search.domain.usecase.LoadTrackDetailsUseCase
import com.ticketswap.extention.exception.Failure
import com.ticketswap.platform.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val loadArtistDetailsUseCase: LoadArtistDetailsUseCase,
    private val loadTrackDetailsUseCase: LoadTrackDetailsUseCase
) : BaseViewModel() {

    private val _detailsLiveDate = MutableLiveData<ItemDetailsDataModel>()
    val detailsLiveData: LiveData<ItemDetailsDataModel> = _detailsLiveDate

    fun loadArtistDetails(id: String, type: SearchItemType) {
        when (type) {
            SearchItemType.ARTIST -> loadArtistDetailsUseCase.execute(observer = DetailsObserver(), params = id)
            SearchItemType.TRACK -> loadTrackDetailsUseCase.execute(observer = DetailsObserver(), params = id)
            else -> handleFailure(NullQueryFailure())
        }
    }

    private inner class DetailsObserver : DisposableSingleObserver<ItemDetailsDataModel>() {
        override fun onSuccess(t: ItemDetailsDataModel) {
            setLoading(false)
            _detailsLiveDate.postValue(t)
        }

        override fun onError(e: Throwable?) {
            setLoading(false)
            handleFailure(Failure.NetworkConnection)
        }
    }

    companion object {
        private const val TAG = "DetailsViewModel"
    }
}
