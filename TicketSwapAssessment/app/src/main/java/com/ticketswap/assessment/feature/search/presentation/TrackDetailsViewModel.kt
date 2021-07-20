package com.ticketswap.assessment.feature.search.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.feature.search.domain.datamodel.TrackDetailsDataModel
import com.ticketswap.assessment.feature.search.domain.usecase.LoadTrackDetailsUseCase
import com.ticketswap.extention.Failure
import com.ticketswap.network.UnauthorizedException
import com.ticketswap.platform.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class TrackDetailsViewModel @Inject constructor(
    private val loadTrackDetailsUseCase: LoadTrackDetailsUseCase
) : BaseViewModel() {

    private val _detailsLiveDate = MutableLiveData<TrackDetailsDataModel>()
    val details: LiveData<TrackDetailsDataModel> = _detailsLiveDate

    fun loadDetails(id: String) {
        Log.d(TAG, "loadDetails: $id")
        setLoading(true)
        loadTrackDetailsUseCase.execute(observer = DetailsObserver(), params = id)
    }

    private inner class DetailsObserver : DisposableSingleObserver<TrackDetailsDataModel>() {
        override fun onSuccess(t: TrackDetailsDataModel) {
            setLoading(false)
            _detailsLiveDate.postValue(t)
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "onError: $e")
            setLoading(false)
            if (e is UnauthorizedException) {
                handleFailure(Failure.UnauthorizedError)
            }
            handleFailure(Failure.NetworkConnection)
        }
    }

    companion object {
        private const val TAG = "DetailsViewModel"
    }
}
