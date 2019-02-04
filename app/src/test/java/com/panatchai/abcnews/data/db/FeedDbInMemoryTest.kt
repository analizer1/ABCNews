package com.panatchai.abcnews.data.db

import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.panatchai.abcnews.data.model.NewsFeedData
import com.panatchai.abcnews.rule.InstantTaskExecutorRule
import org.junit.After
import org.junit.Rule
import org.junit.Test

@Suppress("TestFunctionName")
class FeedDbInMemoryTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val feed = mock<NewsFeedData>()
    private val anotherFeed = mock<NewsFeedData>()
    private val db: FeedDb = FeedDbInMemory()

    @Test
    fun GivenEmptyDB_LoadReturnEmptyList() {
        // init mocks
        val observer = mock<Observer<List<NewsFeedData>>>()

        // execute test
        db.load().observeForever(observer)

        // verify
        verify(observer, times(1)).onChanged(listOf())
    }

    @Test
    fun GivenNonEmptyDB_LoadReturnNonEmptyList() {
        // init mocks
        populateFeeds()
        val observer = mock<Observer<List<NewsFeedData>>>()

        // execute test
        db.load().observeForever(observer)

        // verify
        verify(observer, times(1)).onChanged(listOf(feed))
    }

    @Test
    fun GivenEmptyDB_WhenSave_ReturnAllSavedFeeds() {
        // init mocks
        val observer = mock<Observer<List<NewsFeedData>>>()

        // execute test
        db.save(feed)
        db.load().observeForever(observer)

        // verify
        verify(observer, times(1)).onChanged(listOf(feed))
    }

    @Test
    fun GivenNonEmptyDB_WhenSaveExisting_ReturnCurrentSavedFeeds() {
        // init mocks
        populateFeeds()
        val observer = mock<Observer<List<NewsFeedData>>>()

        // execute test
        db.save(feed)
        db.load().observeForever(observer)

        // verify
        verify(observer, times(1)).onChanged(listOf(feed))
    }

    @Test
    fun GivenNonEmptyDB_WhenSaveNonExisting_ReturnAllSavedFeeds() {
        // init mocks
        populateFeeds()
        val observer = mock<Observer<List<NewsFeedData>>>()

        // execute test
        db.save(anotherFeed)
        db.load().observeForever(observer)

        // verify
        verify(observer, times(1)).onChanged(listOf(feed, anotherFeed))
    }

    @Test
    fun GivenEmptyDB_WhenDelete_ReturnEmptyList() {
        // init mocks
        val observer = mock<Observer<List<NewsFeedData>>>()

        // execute test
        db.delete(feed)
        db.load().observeForever(observer)

        // verify
        verify(observer, times(1)).onChanged(listOf())
    }

    @Test
    fun GivenNonEmptyDB_WhenDeleteExistingFeed_ReturnListWithoutDeletedFeed() {
        // init mocks
        populateFeeds()
        val observer = mock<Observer<List<NewsFeedData>>>()

        // execute test
        db.delete(feed)
        db.load().observeForever(observer)

        // verify
        verify(observer, times(1)).onChanged(listOf())
    }

    @Test
    fun GivenNonEmptyDB_WhenDeleteNonExistingFeed_ReturnExistingList() {
        // init mocks
        populateFeeds()
        val observer = mock<Observer<List<NewsFeedData>>>()

        // execute test
        db.delete(anotherFeed)
        db.load().observeForever(observer)

        // verify
        verify(observer, times(1)).onChanged(listOf(feed))
    }

    @Test
    fun GivenEmptyDB_WhenClear_ReturnEmptyList() {
        // init mocks
        val observer = mock<Observer<List<NewsFeedData>>>()

        // execute test
        db.clear()
        db.load().observeForever(observer)

        // verify
        verify(observer, times(1)).onChanged(listOf())
    }

    @Test
    fun GivenNonEmptyDB_WhenClear_ReturnEmptyList() {
        // init mocks
        populateFeeds()
        val observer = mock<Observer<List<NewsFeedData>>>()

        // execute test
        db.clear()
        db.load().observeForever(observer)

        // verify
        verify(observer, times(1)).onChanged(listOf())
    }

    private fun populateFeeds() {
        db.save(feed)
    }

    @After
    fun tearDown() {
        db.clear()
    }
}