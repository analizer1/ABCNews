package com.panatchai.abcnews.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.android.volley.Cache
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.panatchai.abcnews.data.api.ApiResponse

/**
 * Helper to create network response or error.
 */
object ApiUtil {
    fun <T : Any> successCall(data: T) = createCall(Response.success(data, Cache.Entry()))

    fun <T : Any> createCall(response: Response<T>) = MutableLiveData<ApiResponse<T>>().apply {
        value = ApiResponse.create(response)
    } as LiveData<ApiResponse<T>>

    fun createError(msg: String): VolleyError {
        return VolleyError(msg)
    }

    fun createError(code: Int): VolleyError {
        val networkResponse = NetworkResponse(
            code,
            ByteArray(0),
            false,
            1000L,
            listOf()
        )
        return VolleyError(networkResponse)
    }

    const val FEED_TITLE = "Just In"
    const val FEED_DATE = "2019-01-23 05:37:59"
    const val FEED_LINK = "http://www.abc.net.au/news/2019-01-23/nathan-tinkler-says-hes-an-average-guy/10739412"
    const val FEED_THUMBNAIL = "http://www.abc.net.au/news/image/10739308-4x3-140x105.jpg"
    const val FEED_IMG_URL = "http://www.abc.net.au/news/image/10739308-16x9-2150x1210.jpg"
    const val SUCCESS_JSON = """
            {
                "feed": {
                    "url": "http://www.abc.net.au/news/feed/51120/rss.xml",
                    "title": "$FEED_TITLE",
                    "link": "http://www.abc.net.au/news/justin/",
                    "image": "http://www.abc.net.au/news/image/8413416-1x1-144x144.png"
                },
                "items": [
                    {
                      "title": "Former billionaire Nathan Tinkler says he's an average guy who's had a 'pretty rough trot'",
                      "pubDate": "$FEED_DATE",
                      "link": "$FEED_LINK",
                      "thumbnail": "$FEED_THUMBNAIL",
                      "enclosure": {
                        "link": "$FEED_IMG_URL",
                        "type": "image/jpeg",
                        "thumbnail": "http://www.abc.net.au/news/image/10739308-4x3-140x105.jpg"
                      }
                    }
                ]
            }
        """
}
