package com.ticketswap.assessment.feature.search.domain.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyDataModel(
    val name: String,
    val image: String,
    val type: SearchItemType,
    val id: String
) : Parcelable
