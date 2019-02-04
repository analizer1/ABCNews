package com.panatchai.abcnews.data.mapper

import com.panatchai.abcnews.data.model.NewsFeedData
import com.panatchai.abcnews.ui.model.NewsFeed

/**
 * Map data-layer vo to presentation-layer vo
 */
fun List<NewsFeedData>.map(): List<NewsFeed> {
    val feedList = mutableListOf<NewsFeed>()
    forEach { data ->
        feedList.add(data.map())
    }
    return feedList
}

/**
 * Map data-layer vo to presentation-layer vo
 */
fun NewsFeedData.map(): NewsFeed {
    return NewsFeed(title, pubDate, thumbnail, enclosure.link)
}