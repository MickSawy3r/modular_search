package com.ticketswap.assessment.feature.search.domain.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchListItemDataModel(
    val name: String,
    val searchItemType: SearchItemType
) : Parcelable
