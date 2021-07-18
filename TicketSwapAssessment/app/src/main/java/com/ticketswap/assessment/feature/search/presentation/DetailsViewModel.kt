package com.ticketswap.assessment.feature.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.feature.search.domain.datamodel.ItemDetailsDataModel
import com.ticketswap.platform.core.BaseViewModel

class DetailsViewModel : BaseViewModel() {

    private val _detailsLiveDate = MutableLiveData<ItemDetailsDataModel>()
    val detailsLiveData: LiveData<ItemDetailsDataModel> = _detailsLiveDate

    fun loadDetails() {

    }
}
