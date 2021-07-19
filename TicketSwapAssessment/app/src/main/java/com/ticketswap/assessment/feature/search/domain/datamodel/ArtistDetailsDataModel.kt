package com.ticketswap.assessment.feature.search.domain.datamodel

data class ArtistDetailsDataModel(
    val id: String,
    val name: String,
    val image: String,
    val genres: List<String>,
    val followers: Int
)
