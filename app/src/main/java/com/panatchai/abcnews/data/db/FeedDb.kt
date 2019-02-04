package com.panatchai.abcnews.data.db

import android.arch.lifecycle.LiveData
import com.panatchai.abcnews.data.model.NewsFeedData

/**
 * An abstract definition of Feed database.
 */
interface FeedDb {

    /**
     * Load feeds from database.
     *
     * @return List of [NewsFeedData].
     */
    fun load(): LiveData<List<NewsFeedData>>

    /**
     * Save a [NewsFeedData] into database.
     *
     * @param data feed to save.
     */
    fun save(data: NewsFeedData)

    /**
     * Delete a [NewsFeedData] from database.
     *
     * @param data feed to delete.
     */
    fun delete(data: NewsFeedData)

    /**
     * Delete all feeds saved inside database.
     */
    fun clear()
}