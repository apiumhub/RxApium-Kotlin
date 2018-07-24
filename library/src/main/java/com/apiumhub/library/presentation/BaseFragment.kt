package com.apiumhub.library.presentation

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apiumhub.library.LifecycleAwareView
import com.apiumhub.library.LifecycleEvent
import com.apiumhub.library.subscribeUntil
import io.reactivex.subjects.PublishSubject


abstract class BaseFragment<T: ViewDataBinding>: Fragment(), LifecycleAwareView {

    lateinit var binding: T

    abstract fun getLayoutId(): Int

    protected val disposeBag: PublishSubject<Any> = PublishSubject.create<Any>()

    private val lifecycleEvents = PublishSubject.create<LifecycleEvent>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        lifecycleEvents.onNext(LifecycleEvent.START)
    }

    override fun onStop() {
        super.onStop()
        lifecycleEvents.onNext(LifecycleEvent.STOP)
        disposeBag.onNext(Unit)
    }

    override fun onViewStart(func: () -> Unit) {
        lifecycleEvents.filter { it == LifecycleEvent.START }.subscribeUntil(disposeBag) {
            func()
        }
    }

    override fun onViewStop(func: () -> Unit) {
        lifecycleEvents.filter { it == LifecycleEvent.STOP }.subscribeUntil(disposeBag) {
            func()
        }
    }
}