package com.ticketswap.assessment.feature.search.domain.usecase

import com.ticketswap.assessment.feature.search.data.SpotifyRepository
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel
import com.ticketswap.reactive.executor.PostExecutionThread
import com.ticketswap.reactive.executor.ThreadExecutor
import com.ticketswap.reactive.interactor.ObservableUseCase
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetLastSearchUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val spotifyRepository: SpotifyRepository
) : ObservableUseCase<List<SearchListItemDataModel>, Unit>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Unit?): Observable<List<SearchListItemDataModel>> {
        return spotifyRepository.getLastSearch().map { listOf(it) }
    }
}
