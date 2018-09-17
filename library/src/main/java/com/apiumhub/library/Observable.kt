package com.apiumhub.library

import io.reactivex.Observable

import io.reactivex.ObservableSource
import io.reactivex.disposables.Disposable

typealias InteractorSuccessEvent<T> = ((T) -> Unit) -> Disposable
typealias InteractorErrorEvent = ((Throwable) -> Unit) -> Disposable

inline fun <T> Observable<T>.subscribeUntil(other: ObservableSource<in Any>,
                                            crossinline onNext: (param: T) -> Unit): Disposable {
    return this.takeUntil(other).subscribe { onNext(it) }
}

inline fun <T> Observable<T>.subscribeUntil(other: ObservableSource<in Any>,
                                            crossinline onNext: (param: T) -> Unit,
                                            crossinline onError: (error: Throwable) -> Unit): Disposable {
    return this.takeUntil(other).subscribe({ onNext(it) }, { onError(it) })
}

inline fun <T> Observable<T>.subscribeUntil(other: ObservableSource<in Any>,
                                            crossinline onNext: (param: T) -> Unit,
                                            crossinline onError: (error: Throwable) -> Unit,
                                            crossinline onComplete: () -> Unit): Disposable {
    return this.takeUntil(other).subscribe(
            { onNext(it) },
            { onError(it) },
            { onComplete() })
}

inline fun <T> Observable<T>.subscribeUntil(other: ObservableSource<in Any>,
                                            crossinline onNext: (param: T) -> Unit,
                                            crossinline onError: (error: Throwable) -> Unit,
                                            crossinline onComplete: () -> Unit,
                                            crossinline onSubscribe: (disposable: Disposable) -> Unit): Disposable {
    return this.takeUntil(other).subscribe(
            { onNext(it) },
            { onError(it) },
            { onComplete() },
            { onSubscribe(it) })
}

fun <T> Observable<T>.subscribeSuccess(onNext: (param: T) -> Unit): Disposable = subscribe { onNext(it) }

fun <T> Observable<T>.subscribeError(onError: (error: Throwable) -> Unit): Disposable = this.subscribe(null) { onError(it) }

fun <T> createSuccessEvent(obs: Observable<T>): InteractorSuccessEvent<T> {
    return obs::subscribeSuccess
}

fun <T> createErrorEvent(obs: Observable<T>): InteractorErrorEvent {
    return obs::subscribeError
}

