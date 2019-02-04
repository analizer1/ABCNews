package com.panatchai.abcnews.data.api

import android.arch.lifecycle.MutableLiveData
import com.android.volley.Cache
import com.android.volley.Response
import com.android.volley.VolleyError
import com.panatchai.abcnews.data.util.getErrorMessage

/**
 * Custom LiveData class that deliver network response and error.
 */
class ResponseLiveData<T> : MutableLiveData<ApiResponse<T>>(), Response.Listener<T>, Response.ErrorListener {

    override fun onErrorResponse(error: VolleyError?) {
        if (error != null) {
            postValue(ApiResponse.create(error))
        } else {
            val errorMessage = error?.getErrorMessage()
            postValue(ApiResponse.create(Throwable(errorMessage)))
        }
    }

    override fun onResponse(response: T) {
        postValue(ApiResponse.create(Response.success(response, Cache.Entry())))
    }
}