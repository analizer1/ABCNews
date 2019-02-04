package com.panatchai.abcnews.util

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.BaseHttpStack
import com.android.volley.toolbox.HttpResponse
import java.io.IOException

class MockHttpStack : BaseHttpStack() {
    private var mResponseToReturn: HttpResponse? = null

    private var mExceptionToThrow: IOException? = null

    var lastUrl: String? = null
        private set

    var lastPostBody: ByteArray? = null
        private set

    fun setResponse(response: HttpResponse) {
        mResponseToReturn = response
    }

    fun setException(exception: IOException) {
        mExceptionToThrow = exception
    }

    override fun executeRequest(request: Request<*>?, additionalHeaders: MutableMap<String, String>?): HttpResponse? {
        mExceptionToThrow?.let {
            throw it
        }

        lastUrl = request?.url

        lastPostBody = try {
            request?.body
        } catch (e: AuthFailureError) {
            null
        }

        return mResponseToReturn
    }
}
