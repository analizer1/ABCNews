package com.panatchai.abcnews.data.api

import com.android.volley.Cache
import com.android.volley.Response
import com.android.volley.VolleyError
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.panatchai.abcnews.rule.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test

@Suppress("TestFunctionName")
class ResponseLiveDataTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun GivenError_EmitError() {
        // init mocks
        val rld = ResponseLiveData<String>()
        val liveData = spy(rld)

        // execute test
        liveData.onErrorResponse(VolleyError("error"))

        // verify
        verify(liveData, times(1)).postValue(ApiResponse.create(Throwable("error")))
        val response = mock<Response<String>>()
        verify(liveData, never()).postValue(ApiResponse.create(response))
    }

    @Test
    fun GivenResponse_EmitResponse() {
        // init mocks
        val rld = ResponseLiveData<String>()
        val liveData = spy(rld)

        // execute test
        liveData.onResponse("response")

        // verify
        verify(liveData, times(1))
            .postValue(ApiResponse.create(Response.success("response", Cache.Entry())))
        verify(liveData, never()).postValue(ApiResponse.create(Throwable("error")))
    }
}