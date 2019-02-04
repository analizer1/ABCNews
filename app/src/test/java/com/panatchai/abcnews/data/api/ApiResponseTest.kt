package com.panatchai.abcnews.data.api

import com.android.volley.Cache
import com.android.volley.Response
import com.panatchai.abcnews.util.ApiUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("foo")
        val (errorMessage) = ApiResponse.create<String>(exception)
        assertThat<String>(errorMessage, `is` ("foo"))
    }

    @Test
    fun success() {
        val apiResponse: ApiSuccessResponse<String> = ApiResponse
            .create<String>(Response.success("foo", Cache.Entry())) as ApiSuccessResponse<String>
        assertThat<String>(apiResponse.body, `is` ("foo"))
    }

    @Test
    fun error() {
        val errorResponse = ApiUtil.createError("blah")
        val (errorMessage) = ApiResponse.create<String>(errorResponse)
        assertThat<String>(errorMessage, `is` ("blah"))
    }

    @Test
    fun empty() {
        val body: String? = null
        val success = Response.success(body, Cache.Entry())
        val apiResponse = ApiResponse.create(success)
        assertThat(apiResponse, instanceOf(ApiEmptyResponse::class.java))
    }
}