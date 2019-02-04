package com.panatchai.abcnews.data.api

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser

/**
 * Make a GET request and return a parsed object from JSON.
 *
 * @param url   url to open.
 * @param liveData  deliver network response to the caller.
 * @param parserBlock   function call that parse JSONObject to model class.
 */
class CustomRequest<T>(
    url: String,
    private val liveData: ResponseLiveData<T>,
    private val parserBlock: (response: NetworkResponse?) -> T
) : Request<T>(Method.GET, url, null) {

    override fun deliverResponse(response: T) {
        liveData.onResponse(response)
    }

    override fun deliverError(error: VolleyError?) {
        super.deliverError(error)
        liveData.onErrorResponse(error)
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            Response.success(
                parserBlock(response),
                HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }
}