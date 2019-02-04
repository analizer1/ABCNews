package com.panatchai.abcnews.util

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProvider.Factory
import android.support.annotation.MainThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity

/**
 * Save and restore ViewModel throughout the Activity's LifeCycle.
 */
object ViewModelProviders {

    @NonNull
    @MainThread
    fun of(@NonNull activity: FragmentActivity, @Nullable vmFactory: Factory?): ViewModelProvider {
        var factory = vmFactory
        val application = checkApplication(activity)
        if (factory == null) {
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return ViewModelProvider(activity.viewModelStore, factory)
    }

    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call."
            )
    }
}