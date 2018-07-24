package com.apiumhub.library

interface LifecycleAwareService {
    fun start() {}
    fun stop() {}
}

interface LifecycleAwareView {
    fun onViewStart(func: () -> Unit)
    fun onViewStop(func: () -> Unit)
}

abstract class LifecycleAwarePresenter(lifecycleAwareView: LifecycleAwareView, lifecycleAwareService: LifecycleAwareService) {
    init {
        lifecycleAwareView.onViewStart(lifecycleAwareService::start)
        lifecycleAwareView.onViewStop(lifecycleAwareService::stop)
    }
}

enum class LifecycleEvent {
    START,
    STOP
}