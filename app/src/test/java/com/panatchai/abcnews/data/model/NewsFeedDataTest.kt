package com.panatchai.abcnews.data.model

import com.panatchai.abcnews.util.ApiUtil
import org.json.JSONException
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("TestFunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class NewsFeedDataTest {

    @Test(expected = JSONException::class)
    fun GivenInvalidJson_ThrowError() {
        val invalidJson = """
            "unknown_key": "some data"
        """.trimIndent()
        JsonResponse(invalidJson)
    }

    @Test
    fun GivenValidJson_ReturnResponse() {
        val response = JsonResponse(ApiUtil.SUCCESS_JSON)
        assertNotNull(response)
        assertNotNull(response.feedItems)
        assertFalse(response.feedItems?.isEmpty() ?: true)
    }
}