package com.panatchai.abcnews.data.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.android.volley.Cache
import com.android.volley.Response
import com.android.volley.VolleyError
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.panatchai.abcnews.data.api.ApiResponse
import com.panatchai.abcnews.data.api.FeedApi
import com.panatchai.abcnews.data.db.FeedDb
import com.panatchai.abcnews.data.model.JsonResponse
import com.panatchai.abcnews.data.model.NewsFeedData
import com.panatchai.abcnews.data.model.Resource
import com.panatchai.abcnews.rule.InstantTaskExecutorRule
import com.panatchai.abcnews.util.InstantAppExecutors
import org.junit.Rule
import org.junit.Test

@Suppress("TestFunctionName")
class FeedRepositoryTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val appExecutors = InstantAppExecutors()
    private val feedApi = mock<FeedApi>()
    private val db = mock<FeedDb>()

    private val repo: FeedRepository = FeedRepository(appExecutors, feedApi, db)

    @Test
    fun WhenRefresh_ReturnFeedsFromApi() {
        // init mocks
        val observer = mock<Observer<Resource<List<NewsFeedData>>>>()
        val response = mock<JsonResponse>()
        val dbFeed = mock<NewsFeedData>()
        val apiFeed = mock<NewsFeedData>()
        val responseLiveData = MutableLiveData<ApiResponse<JsonResponse>>()
        val dbLoadLiveData = MutableLiveData<List<NewsFeedData>>()
        val dbDataList = mutableListOf(dbFeed)
        whenever(feedApi.getNewsFeed()).thenReturn(responseLiveData)
        whenever(response.feedItems).thenReturn(listOf(apiFeed))
        whenever(db.load()).thenReturn(dbLoadLiveData)
        doNothing().whenever(db).clear()
        doAnswer {
            val arg = it.getArgument<NewsFeedData>(0)
            dbDataList.add(arg)
            null
        }.whenever(db).save(any())

        // execute test
        repo.refresh().observeForever(observer)

        // verify
        verify(observer).onChanged(Resource.loading(null))

        dbLoadLiveData.value = dbDataList
        verify(observer).onChanged(Resource.loading(dbDataList))

        responseLiveData.value = ApiResponse.create(Response.success(response, Cache.Entry()))
        verify(observer).onChanged(Resource.success(dbDataList))
    }

    @Test
    fun WhenApiError_ReturnSavedFeeds() {
        // init mocks
        val observer = mock<Observer<Resource<List<NewsFeedData>>>>()
        val response = mock<JsonResponse>()
        val feed = mock<NewsFeedData>()
        val responseLiveData = MutableLiveData<ApiResponse<JsonResponse>>()
        val dbLoadLiveData = MutableLiveData<List<NewsFeedData>>()
        whenever(feedApi.getNewsFeed()).thenReturn(responseLiveData)
        whenever(response.feedItems).thenReturn(listOf(feed))
        whenever(db.load()).thenReturn(dbLoadLiveData)
        doNothing().whenever(db).clear()
        doNothing().whenever(db).save(any())

        // execute test
        repo.refresh().observeForever(observer)

        // verify
        verify(observer).onChanged(Resource.loading(null))

        dbLoadLiveData.value = listOf(feed)
        verify(observer).onChanged(Resource.loading(listOf(feed)))

        val error = VolleyError("error")
        responseLiveData.value = ApiResponse.create(Response.error(error))
        verify(observer).onChanged(Resource.error("error", listOf(feed)))
    }

    @Test
    fun WhenDeleteAll_RemoveAllEntriesInDatabase() {
        // init mocks
        doNothing().whenever(db).clear()

        // execute test
        repo.deleteAll()

        // verify
        verify(db, times(1)).clear()
    }
}