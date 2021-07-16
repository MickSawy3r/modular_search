package com.ticketswap.interactor

import com.ticketswap.executor.PostExecutionThread
import com.ticketswap.executor.ThreadExecutor
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

open class BaseReactiveUseCase constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) {

    protected val threadExecutorScheduler: Scheduler = Schedulers.from(threadExecutor)

    protected val postExecutionThreadScheduler: Scheduler = postExecutionThread.scheduler

    private val disposables = CompositeDisposable()

    open fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
