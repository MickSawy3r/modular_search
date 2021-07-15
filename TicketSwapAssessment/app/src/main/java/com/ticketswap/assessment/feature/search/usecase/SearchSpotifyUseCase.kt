package com.ticketswap.assessment.feature.search.usecase

import com.ticketswap.assessment.core.executor.PostExecutionThread
import com.ticketswap.assessment.core.executor.ThreadExecutor
import com.ticketswap.assessment.core.interactor.SingleUseCase
import com.ticketswap.assessment.feature.search.datamodel.SearchListItemDataModel
import com.ticketswap.assessment.feature.search.SpotifyRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchSpotifyUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val spotifyRepository: SpotifyRepository
) : SingleUseCase<List<SearchListItemDataModel>, String>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseSingle(params: String?): Single<List<SearchListItemDataModel>> {
        return spotifyRepository.search(params ?: "")
    }
}
