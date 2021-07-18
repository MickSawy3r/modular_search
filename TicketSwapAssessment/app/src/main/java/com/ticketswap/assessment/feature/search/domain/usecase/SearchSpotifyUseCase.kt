package com.ticketswap.assessment.feature.search.domain.usecase

import com.ticketswap.assessment.feature.search.data.SpotifyRepository
import com.ticketswap.assessment.feature.search.domain.datamodel.SearchListItemDataModel
import com.ticketswap.assessment.feature.search.domain.failures.NullQueryFailure
import com.ticketswap.assessment.feature.search.domain.failures.SessionExpiredFailure
import com.ticketswap.authenticator.AuthGuard
import com.ticketswap.reactive.executor.PostExecutionThread
import com.ticketswap.reactive.executor.ThreadExecutor
import com.ticketswap.reactive.interactor.ObservableUseCase
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SearchSpotifyUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val spotifyRepository: SpotifyRepository,
    private val authGuard: AuthGuard
) : ObservableUseCase<List<SearchListItemDataModel>, String>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: String?): Observable<List<SearchListItemDataModel>> {
        if (params == null) {
            return Observable.error(NullQueryFailure())
        }
        if (!authGuard.userLoggedIn()) {
            return Observable.error(SessionExpiredFailure())
        }
        return spotifyRepository.search(params, authGuard.getAuthToken())
    }
}
