package com.ticketswap.assessment.feature.search.network

import com.ticketswap.assessment.feature.search.datasource.network.ISpotifyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SearchNetworkModule {

    @Provides
    fun provideSpotifyApi(): ISpotifyApi {
        return SearchApi()
    }
}
