package com.panatchai.abcnews.data.mapper

import com.panatchai.abcnews.data.model.JsonResponse
import com.panatchai.abcnews.data.model.NewsFeedData
import com.panatchai.abcnews.util.ApiUtil
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("TestFunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class NewsFeedMapperTest {

    @Test
    fun GivenNewsFeedData_ReturnNewsFeed() {
        val jsonResponse = JsonResponse(ApiUtil.SUCCESS_JSON)
        val newsFeedData = jsonResponse.feedItems?.get(0)

        assertNotNull(newsFeedData)
        newsFeedData?.let {
            val map = newsFeedData.map()
            assertThat<String>(map.title, `is`(it.title))
            assertThat<String>(map.date, `is`(it.pubDate))
            assertThat<String>(map.thumbnail, `is`(it.thumbnail))
            assertThat<String>(map.imgUrl, `is`(it.enclosure.link))
        }
    }

    @Test
    fun GivenEmptyList_WhenMap_ReturnEmptyList() {
        // init mocks
        val newsFeedDataList = listOf<NewsFeedData>()

        // execute test
        val mapList = newsFeedDataList.map()

        // verify
        assertTrue(mapList.isEmpty())
    }

    @Test
    fun GivenNonEmptyList_WhenMap_ReturnNonEmptyList() {
        // init mocks
        val jsonResponse = JsonResponse(ApiUtil.SUCCESS_JSON)
        val newsFeedDataList = jsonResponse.feedItems

        // execute test
        val mapList = newsFeedDataList?.map()

        // verify
        assertNotNull(jsonResponse)
        assertNotNull(newsFeedDataList)
        assertNotNull(mapList)
        assertFalse(mapList?.isEmpty() ?: true)
    }
}