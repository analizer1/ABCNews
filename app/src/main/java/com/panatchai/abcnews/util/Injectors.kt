package com.panatchai.abcnews.util

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingComponent
import android.support.v4.app.FragmentActivity
import com.panatchai.abcnews.ui.binding.FeedDataBindingComponent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A lazy injector that gets cleaned up when the Activity is destroyed.
 *
 * Accessing this variable in a destroyed Activity will throw NPE.
 *
 * This just imitates the Dagger's Injector since the test does not allow using other lib (e.g. Dagger2).
 */
open class Injector<T : Any>(activity: FragmentActivity, value: T?) : ReadWriteProperty<FragmentActivity, T> {
    private var _value: T? = value

    init {
        activity.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                _value = null
            }
        })
    }

    override fun getValue(thisRef: FragmentActivity, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException(
            "should never call injector get when it might not be available"
        )
    }

    override fun setValue(thisRef: FragmentActivity, property: KProperty<*>, value: T) {
        _value = value
    }
}

/**
 * Creates an [Injector] associated with Activity.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> FragmentActivity.inject(): Injector<T> {
    return when (T::class) {
        AppExecutors::class -> {
            AppExecutorsInjector(this)
        }

        DataBindingComponent::class -> {
            FeedDataBindingComponentInjector(this)
        }

        ViewModelProvider.Factory::class -> {
            ViewModelFactoryInjector(this)
        }

        else -> throw IllegalStateException(
            "${T::class} is unknown"
        )
    } as Injector<T>
}

class AppExecutorsInjector(
    activity: FragmentActivity
) : Injector<AppExecutors>(
    activity,
    DependencyModule.getInstance(activity).provideAppExecutors()
)

class FeedDataBindingComponentInjector(
    activity: FragmentActivity
) : Injector<FeedDataBindingComponent>(activity, FeedDataBindingComponent(activity))

class ViewModelFactoryInjector(
    activity: FragmentActivity
) : Injector<ViewModelProvider.Factory>(
    activity,
    DependencyModule.getInstance(activity).provideFeedViewModelFactory()
)