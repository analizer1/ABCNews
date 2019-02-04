package com.panatchai.abcnews.data.util

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BaseHttpStack
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

/**
 * Handling Volley network calls.
 */
class RequestUtil constructor(context: Context, stack: BaseHttpStack? = null) {

    private var baseHttpStack: BaseHttpStack? = stack

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap? {
                    return cache.get(url)
                }

                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext, baseHttpStack)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    fun cancelRequests(tag: String) {
        requestQueue.cancelAll(tag)
    }

    companion object {
        @Volatile
        private var INSTANCE: RequestUtil? = null

        fun getInstance(context: Context, stack: BaseHttpStack? = null) =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: RequestUtil(context, stack).also {
                        INSTANCE = it
                    }
            }
    }
}