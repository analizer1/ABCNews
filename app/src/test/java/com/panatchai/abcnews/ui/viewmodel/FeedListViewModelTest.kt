package com.panatchai.abcnews.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.panatchai.abcnews.data.model.Resource
import com.panatchai.abcnews.domain.usecase.UseCase
import com.panatchai.abcnews.rule.InstantTaskExecutorRule
import com.panatchai.abcnews.ui.model.NewsFeed
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

class FeedListViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val useCase = mock<UseCase<Void, List<NewsFeed>>>()
    private val viewModel = FeedListViewModel(useCase)

    @Test
    fun testRefreshFeeds() {
        // init mocks
        val mutableLiveData = MutableLiveData<Resource<List<NewsFeed>>>()
        val observer = mock<Observer<Resource<List<NewsFeed>>>>()
        whenever(useCase.run()).thenReturn(mutableLiveData)

        // execute test
        val liveData = viewModel.refreshFeeds()
        liveData.observeForever(observer)

        // verify
        assertNotNull(liveData)

        mutableLiveData.value = Resource.loading(null)
        verify(observer).onChanged(Resource.loading(null))

        mutableLiveData.value = Resource.loading(listOf())
        verify(observer).onChanged(Resource.loading(listOf()))

        val feed = NewsFeed("", "", "", "")
        mutableLiveData.value = Resource.loading(listOf(feed))
        verify(observer).onChanged(Resource.loading(listOf(feed)))

        mutableLiveData.value = Resource.success(listOf(feed))
        verify(observer).onChanged(Resource.success(listOf(feed)))

        mutableLiveData.value = Resource.error("error", null)
        verify(observer).onChanged(Resource.error("error", null))

        mutableLiveData.value = Resource.error("error", listOf(feed))
        verify(observer).onChanged(Resource.error("error", listOf(feed)))
    }
}