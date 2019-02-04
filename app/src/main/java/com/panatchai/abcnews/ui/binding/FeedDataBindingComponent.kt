package com.panatchai.abcnews.ui.binding

import android.databinding.DataBindingComponent
import android.support.v4.app.FragmentActivity
import com.panatchai.abcnews.data.util.RequestUtil

/**
 * Custom binding.
 */
class FeedDataBindingComponent(activity: FragmentActivity) : DataBindingComponent {

    private val adapter = FeedImageBindingAdapter(RequestUtil.getInstance(activity).imageLoader)

    override fun getFeedImageBindingAdapter() = adapter
}