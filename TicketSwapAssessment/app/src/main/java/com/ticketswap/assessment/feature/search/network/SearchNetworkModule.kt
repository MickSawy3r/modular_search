package com.ticketswap.assessment.feature.search.network

import com.ticketswap.assessment.BuildConfig
import com.ticketswap.assessment.feature.search.datasource.network.ISpotifyApi
import com.ticketswap.network.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SearchNetworkModule {

    @Provides
    fun provideNetworkClient(): NetworkClient {
        return NetworkClient(baseUrl = BuildConfig.SPOTIFY_BASE_URL)
    }

    @Provides
    fun provideSpotifyApi(networkClient: NetworkClient): ISpotifyApi {
        return SearchApi(networkClient)
    }
}
