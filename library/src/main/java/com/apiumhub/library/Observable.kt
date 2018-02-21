package com.apiumhub.library

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.disposables.Disposable

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
