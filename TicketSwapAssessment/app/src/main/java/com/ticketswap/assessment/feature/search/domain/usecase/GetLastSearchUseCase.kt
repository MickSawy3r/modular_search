package com.ticketswap.assessment.feature.search.domain.usecase

import com.ticketswap.assessment.feature.search.data.SpotifyRepository
import com.ticketswap.assessment.feature.search.domain.datamodel.SpotifyDataModel
import com.ticketswap.reactive.executor.PostExecutionThread
import com.ticketswap.reactive.executor.ThreadExecutor
import com.ticketswap.reactive.interactor.SingleUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetLastSearchUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val spotifyRepository: SpotifyRepository
) : SingleUseCase<List<SpotifyDataModel>, Unit>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseSingle(params: Unit?): Single<List<SpotifyDataModel>> {
        return spotifyRepository.getCached()
    }
}
