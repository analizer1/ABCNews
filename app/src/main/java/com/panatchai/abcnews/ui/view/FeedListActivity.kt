package com.panatchai.abcnews.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.panatchai.abcnews.R
import com.panatchai.abcnews.data.model.Status
import com.panatchai.abcnews.databinding.ActivityMainBinding
import com.panatchai.abcnews.ui.common.RetryCallback
import com.panatchai.abcnews.ui.viewmodel.FeedListViewModel
import com.panatchai.abcnews.util.AppExecutors
import com.panatchai.abcnews.util.ViewModelProviders
import com.panatchai.abcnews.util.autoCleared
import com.panatchai.abcnews.util.inject

class FeedListActivity : AppCompatActivity(), RefreshableView, RetryCallback {

    internal var viewModelFactory: ViewModelProvider.Factory by inject()
    internal var appExecutors: AppExecutors by inject()
    internal lateinit var feedListViewModel: FeedListViewModel

    private var binding by autoCleared<ActivityMainBinding>()
    private var feedListAdapter: FeedListAdapter by autoCleared()
    private var dataBindingComponent: DataBindingComponent by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewModel()
        initFeedAdapter()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun retry() {
        refresh()
    }

    override fun refresh() {
        feedListViewModel.refreshFeeds().observe(this, Observer { result ->
            binding.swipeRefresh.isRefreshing = result?.status == Status.LOADING

            result?.let { resource ->
                binding.feedResource = resource
                feedListAdapter.submitList(resource.data)
            }
        })
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.refresher = this
        binding.retryCallback = this
    }

    private fun initViewModel() {
        feedListViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FeedListViewModel::class.java)
    }

    private fun initFeedAdapter() {
        feedListAdapter = FeedListAdapter(
            dataBindingComponent,
            appExecutors,
            null
        )
        binding.feedList.adapter = feedListAdapter
    }
}
