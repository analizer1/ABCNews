package com.panatchai.abcnews.ui.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.panatchai.abcnews.util.DependencyModule

/**
 * Manufacture [ViewModel] or, in this case, [FeedListViewModel] to be exact.
 */
class FeedViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // bcuz we only have 1 ViewModel right now so simplified solution is
        val feedListUseCase = DependencyModule.getInstance(context).provideFeedListUseCase()
        return FeedListViewModel(feedListUseCase) as T
    }
}