package com.panatchai.abcnews.data.api

import android.arch.lifecycle.LiveData
import android.content.Context
import com.android.volley.toolbox.HttpHeaderParser
import com.panatchai.abcnews.data.model.JsonResponse
import com.panatchai.abcnews.data.util.RequestUtil
import org.json.JSONException
import java.nio.charset.Charset

/**
 * Call API to get news feed.
 *
 * @param context should use Application's context to prevent leak.
 */
class FeedApi(private val context: Context) {

    fun getNewsFeed(): LiveData<ApiResponse<JsonResponse>> {
        val newsFeedLive = ResponseLiveData<JsonResponse>()
        val volleyRequest = CustomRequest(URL, newsFeedLive) { response ->
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            try {
                JsonResponse(json)
            } catch (e: JSONException) {
                JsonResponse("")
            }
        }

        volleyRequest.tag = TAG
        RequestUtil.getInstance(context).addToRequestQueue(volleyRequest)
        return newsFeedLive
    }

    companion object {
        const val URL = "https://api.rss2json.com/v1/api.json?rss_url=http://www.abc.net.au/news/feed/51120/rss.xml"
        const val TAG = "FeedsRequestTag"
    }
}