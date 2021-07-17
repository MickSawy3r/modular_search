package com.ticketswap.reactive.interactor

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.observers.DisposableObserver

open class EmptyObserver<T> : DisposableObserver<T>() {

    override fun onNext(@NonNull t: T) {}

    override fun onError(@NonNull e: Throwable) {}

    override fun onComplete() {}
}
