package com.ticketswap.assessment.feature.search.usecase

import com.ticketswap.executor.PostExecutionThread
import com.ticketswap.executor.ThreadExecutor
import com.ticketswap.interactor.SingleUseCase
import com.ticketswap.assessment.feature.search.datamodel.SearchListItemDataModel
import com.ticketswap.assessment.feature.search.SpotifyRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchSpotifyUseCase @Inject constructor(
    threadExecutor: com.ticketswap.executor.ThreadExecutor,
    postExecutionThread: com.ticketswap.executor.PostExecutionThread,
    private val spotifyRepository: SpotifyRepository
) : com.ticketswap.interactor.SingleUseCase<List<SearchListItemDataModel>, String>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseSingle(params: String?): Single<List<SearchListItemDataModel>> {
        return spotifyRepository.search(params ?: "")
    }
}
