@file:Suppress("DEPRECATION")

package com.panatchai.abcnews.domain.usecase

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.panatchai.abcnews.data.mapper.map
import com.panatchai.abcnews.data.model.JsonResponse
import com.panatchai.abcnews.data.model.NewsFeedData
import com.panatchai.abcnews.data.model.Resource
import com.panatchai.abcnews.data.repository.FeedRepository
import com.panatchai.abcnews.rule.InstantTaskExecutorRule
import com.panatchai.abcnews.ui.model.NewsFeed
import com.panatchai.abcnews.util.ApiUtil
import com.panatchai.abcnews.util.InstantAppExecutors
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class NewsFeedUseCaseTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val appExecutors = InstantAppExecutors()
    private val repo = mock<FeedRepository>()

    private val usecase: UseCase<Void, List<NewsFeed>> = NewsFeedUseCase(appExecutors, repo)

    @Test
    fun executeUseCase() {
        // init mocks
        val observer = mock<Observer<Resource<List<NewsFeed>>>>()
        val liveData = MutableLiveData<Resource<List<NewsFeedData>>>()
        val response = JsonResponse(ApiUtil.SUCCESS_JSON)
        val feed = response.feedItems?.get(0)
        val map = feed?.map()
        liveData.value = Resource.success(response.feedItems)
        whenever(repo.refresh()).thenReturn(liveData)

        // execute test
        usecase.run().observeForever(observer)

        // verify
        assertNotNull(response)
        assertNotNull(feed)
        assertNotNull(map)
        verify(observer).onChanged(Resource.success(listOf(map!!)))
    }
}