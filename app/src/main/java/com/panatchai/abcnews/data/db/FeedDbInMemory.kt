package com.panatchai.abcnews.data.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.panatchai.abcnews.data.model.NewsFeedData

/**
 * A specific implementation of [FeedDb] which stores data inside memory.
 *
 * Note: non-thread-safe and will suffer from concurrency.
 */
class FeedDbInMemory : FeedDb {

    // Contains feeds
    private val feedList = mutableListOf<NewsFeedData>()
    private val liveData = MutableLiveData<List<NewsFeedData>>()

    override fun load(): LiveData<List<NewsFeedData>> {
        liveData.postValue(feedList)
        return liveData
    }

    override fun save(data: NewsFeedData) {
        feedList.addIfNotExists(data)
        liveData.postValue(feedList)
    }

    override fun delete(data: NewsFeedData) {
        feedList.remove(data)
        liveData.postValue(feedList)
    }

    override fun clear() {
        feedList.clear()
        liveData.postValue(feedList)
    }

    // this could be generic global extension
    private fun MutableList<NewsFeedData>.addIfNotExists(data: NewsFeedData) {
        val exists = any {
            it == data
        }
        if (!exists) {
            add(data)
        }
    }
}