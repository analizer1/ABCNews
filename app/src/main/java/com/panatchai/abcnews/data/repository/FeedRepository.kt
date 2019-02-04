package com.panatchai.abcnews.data.repository

import android.arch.lifecycle.LiveData
import com.panatchai.abcnews.data.api.ApiResponse
import com.panatchai.abcnews.data.api.FeedApi
import com.panatchai.abcnews.data.db.FeedDb
import com.panatchai.abcnews.data.model.JsonResponse
import com.panatchai.abcnews.data.model.NewsFeedData
import com.panatchai.abcnews.data.model.Resource
import com.panatchai.abcnews.util.AppExecutors

/**
 * Feed repository.
 *
 * @param appExecutors task executor.
 * @param feedApi feed api.
 * @param db feed database.
 */
class FeedRepository constructor(
    private val appExecutors: AppExecutors,
    private val feedApi: FeedApi,
    private val db: FeedDb
) {

    /**
     * Refresh feeds by returning data loaded from database
     * while refreshing the data by calling feed api.
     */
    fun refresh(): LiveData<Resource<List<NewsFeedData>>> {
        return object : NetworkBoundResource<List<NewsFeedData>, JsonResponse>(appExecutors = appExecutors) {
            override fun saveCallResult(item: JsonResponse) {
                deleteAll()
                item.feedItems?.forEach { feed ->
                    db.save(feed)
                }
            }

            override fun shouldFetch(data: List<NewsFeedData>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<NewsFeedData>> {
                return db.load()
            }

            override fun createCall(): LiveData<ApiResponse<JsonResponse>> {
                return feedApi.getNewsFeed()
            }
        }.asLiveData()
    }

    /**
     * Delete all feeds inside database.
     */
    fun deleteAll() {
        db.clear()
    }
}