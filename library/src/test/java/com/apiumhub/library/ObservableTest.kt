package com.apiumhub.library

import io.reactivex.subjects.PublishSubject
import junit.framework.Assert
import org.junit.Test
import java.util.concurrent.CountDownLatch

open class ObservableTest {

    private val detachSignal: PublishSubject<Any> = PublishSubject.create()
    private val observable: PublishSubject<String> = PublishSubject.create()

    @Test
    fun test_subscribe_until_should_call_on_next_and_should_be_disposed() {
        val latch = CountDownLatch(1)
        val subscription = observable.subscribeUntil(detachSignal) {
            latch.countDown()
        }
        observable.onNext("someString2")
        latch.await()
        detachSignal.onNext(Any())
        Assert.assertTrue(subscription.isDisposed)
    }

    @Test
    fun test_subscribe_until_should_call_on_next_and_on_error_and_should_be_disposed() {
        val latch = CountDownLatch(2)
        val subscription = observable.subscribeUntil(detachSignal, {
            latch.countDown()
        }, {
            latch.countDown()
        })
        observable.onNext("someString")
        observable.onError(Throwable())
        latch.await()
        detachSignal.onNext(Any())
        Assert.assertTrue(subscription.isDisposed)
    }

    @Test
    fun test_subscribe_until_should_call_on_next_on_error_and_on_complete_and_should_be_disposed() {
        val latch = CountDownLatch(2)
        val subscription = observable.subscribeUntil(detachSignal, {
            latch.countDown()
        }, {
            latch.countDown()
        }, {
            latch.countDown()
        })
        observable.onNext("someString")
        observable.onComplete()
        latch.await()
        detachSignal.onNext(Any())
        Assert.assertTrue(subscription.isDisposed)
    }

    @Test
    fun test_subscribe_until_should_call_on_next_on_error_on_complete_and_on_subscribe_and_should_be_disposed() {
        val latch = CountDownLatch(3)
        val subscription = observable.subscribeUntil(detachSignal, {
            latch.countDown()
        }, {
            latch.countDown()
        }, {
            latch.countDown()
        }, {
            latch.countDown()
        })
        observable.onNext("someString")
        observable.onComplete()
        latch.await()
        detachSignal.onNext(Any())
        Assert.assertTrue(subscription.isDisposed)
    }
}