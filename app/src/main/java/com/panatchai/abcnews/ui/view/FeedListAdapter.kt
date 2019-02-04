package com.panatchai.abcnews.ui.view

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.panatchai.abcnews.R
import com.panatchai.abcnews.databinding.LayoutLargeFeedItemBinding
import com.panatchai.abcnews.databinding.LayoutRegularFeedItemBinding
import com.panatchai.abcnews.ui.common.DataBoundListAdapter
import com.panatchai.abcnews.ui.model.NewsFeed
import com.panatchai.abcnews.util.AppExecutors

class FeedListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val feedClickCallback: ((NewsFeed) -> Unit)?
) : DataBoundListAdapter<NewsFeed, ViewDataBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<NewsFeed>() {
        override fun areItemsTheSame(p0: NewsFeed, p1: NewsFeed): Boolean {
            return p0 == p1
        }

        override fun areContentsTheSame(p0: NewsFeed, p1: NewsFeed): Boolean {
            return p0 == p1
        }
    }
) {

    private fun inflate(parent: ViewGroup, viewType: Int): InflationResult {
        return when (viewType) {
            LARGE_FEED -> {
                val inflated = DataBindingUtil.inflate<LayoutLargeFeedItemBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_large_feed_item,
                    parent,
                    false,
                    dataBindingComponent
                )
                InflationResult(inflated, inflated.newsFeed)
            }
            else -> {
                val inflated = DataBindingUtil.inflate<LayoutRegularFeedItemBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_regular_feed_item,
                    parent,
                    false,
                    dataBindingComponent
                )
                InflationResult(inflated, inflated.newsFeed)
            }
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val (binding, feed) = inflate(parent, viewType)
        feedClickCallback?.let { callback ->
            binding.root.setOnClickListener {
                feed?.let { feed ->
                    callback.invoke(feed)
                }
            }
        }
        return binding
    }

    override fun getItemViewType(position: Int): Int {
        return if (position > 0) {
            REGULAR_FEED
        } else {
            LARGE_FEED
        }
    }

    override fun bind(binding: ViewDataBinding, item: NewsFeed, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            LARGE_FEED -> {
                with(binding as LayoutLargeFeedItemBinding) {
                    this.position = position
                    this.newsFeed = item
                }
            }
            else -> {
                with(binding as LayoutRegularFeedItemBinding) {
                    this.position = position
                    this.newsFeed = item
                }
            }
        }
    }

    private data class InflationResult(val binding: ViewDataBinding, val item: NewsFeed?)

    companion object {
        private const val LARGE_FEED = 0
        private const val REGULAR_FEED = 1
    }
}