package com.panatchai.abcnews.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.panatchai.abcnews.data.model.Resource
import com.panatchai.abcnews.domain.usecase.UseCase
import com.panatchai.abcnews.ui.model.NewsFeed

/**
 * Suppose to orchestrate view but nothing much to do here.
 */
class FeedListViewModel(
    private val useCase: UseCase<Void, List<NewsFeed>>
) : ViewModel() {

    fun refreshFeeds(): LiveData<Resource<List<NewsFeed>>> {
        return useCase.run()
    }
}