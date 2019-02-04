package com.panatchai.abcnews.data.api

/**
 * No idea how to unit test this cuz I never use Volley before.
 */
//import com.android.volley.Request.Method
//import com.android.volley.RequestQueue
//import com.android.volley.toolbox.HttpResponse
//import com.nhaarman.mockitokotlin2.any
//import com.nhaarman.mockitokotlin2.mock
//import com.nhaarman.mockitokotlin2.never
//import com.nhaarman.mockitokotlin2.verify
//import com.panatchai.abcnews.data.model.JsonResponse
//import com.panatchai.abcnews.data.util.RequestUtil
//import com.panatchai.abcnews.util.ApiUtil.SUCCESS_JSON
//import com.panatchai.abcnews.util.MockHttpStack
//import org.apache.tools.ant.filters.StringInputStream
//import org.junit.After
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.robolectric.RobolectricTestRunner
//import org.robolectric.RuntimeEnvironment
//import org.robolectric.annotation.Config
//
//// Suppress DEPRECATION -> RuntimeEnvironment.application in favor for androidx
//@Suppress("TestFunctionName", "DEPRECATION")
//@RunWith(RobolectricTestRunner::class)
//@Config(manifest = Config.NONE, sdk = [27])
//class CustomRequestTest {
//
//    private lateinit var mockHttpStack: MockHttpStack
//    private lateinit var requestQueue: RequestQueue
//
//    @Before
//    fun setup() {
//        // init mocks
//        mockHttpStack = MockHttpStack()
//        requestQueue = RequestUtil.getInstance(RuntimeEnvironment.application, mockHttpStack).requestQueue
//    }
//
//    @Test
//    fun GivenGetUrl_CreateRequest() {
//        // init mocks
//        val newsFeedLive = mock<ResponseLiveData<JsonResponse>>()
//        val response = mock<JsonResponse>()
//
//        // execute test
//        val request = CustomRequest("url", newsFeedLive) { response }
//
//        // verification
//        assertEquals(request.method, Method.GET)
//        assertEquals(request.url, "url")
//    }
//
//    @Test
//    fun GivenSuccessResponse_DeliveryResponse() {
//        // init mocks
//        val inputStream = StringInputStream(SUCCESS_JSON)
//        val httpResponse = HttpResponse(200, listOf(), inputStream.available(), inputStream)
//        mockHttpStack.setResponse(httpResponse)
//        val newsFeedLive = mock<ResponseLiveData<JsonResponse>>()
//        val response = mock<JsonResponse>()
//        val request = CustomRequest("url", newsFeedLive) { response }
//
//        // execute test
//        requestQueue.add(request)
//
//        // verification
//        verify(newsFeedLive).onResponse(response)
//        verify(newsFeedLive, never()).onErrorResponse(any())
//    }
//
//    @Test
//    fun GivenErrorResponse_DeliveryError() {
//        // init mocks
//        // execute test
//        // verification
//    }
//
//    @After
//    fun tearDown() {
//        // clean up
//        requestQueue.stop()
//    }
//}