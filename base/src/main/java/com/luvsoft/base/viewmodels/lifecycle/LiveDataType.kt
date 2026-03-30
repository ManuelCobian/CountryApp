package com.luvsoft.base.viewmodels.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * The event is dispatch even not exist a consumer,
 */

open class PublishLiveData<T> : MutableLiveData<T>() {

    protected val pending: AtomicBoolean = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(
            owner,
            Observer { t ->
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(t)
                }
            }
        )
    }

    override fun setValue(value: T) {
        if (hasActiveObservers()) pending.set(true)
        super.setValue(value)
    }

    override fun postValue(value: T) {
        if (hasActiveObservers()) pending.set(true)
        super.postValue(value)
    }
}

/**
 * The event will be dispatched until exist an active observable,
 * after that, the data not be dispatch on every observe
 */

class ConsumerLiveData<T> : MutableLiveData<T>() {

    protected val pending: AtomicBoolean = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(
            owner,
            Observer { t ->
                if (hasActiveObservers() && pending.compareAndSet(true, false)) {
                    observer.onChanged(t)
                }
            }
        )
    }

    override fun setValue(value: T) {
        pending.set(true)
        super.setValue(value)
    }

    override fun postValue(value: T) {
        pending.set(true)
        super.postValue(value)
    }
}

/**
 * The Event persist and its dispatch after every observe
 */

typealias BehaviorLiveData<T> = MutableLiveData<T>
