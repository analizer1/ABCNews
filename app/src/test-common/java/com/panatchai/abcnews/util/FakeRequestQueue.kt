package com.panatchai.abcnews.util

import com.android.volley.AuthFailureError
import com.android.volley.Cache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.NoCache

class FakeRequestQueue :
    RequestQueue(NoCache(), BasicNetwork(MockHttpStack())) {
    init {
        start()
    }

    override fun start() {
        println("request start")
        super.start()
    }

    override fun stop() {
        println("request stop")
        super.stop()
    }

    override fun getCache(): Cache {
        println("request start")
        return super.getCache()
    }

    override fun cancelAll(filter: RequestQueue.RequestFilter) {
        println("Request cancel with filter $filter")
        super.cancelAll(filter)
    }

    override fun cancelAll(tag: Any) {
        println("Request cancel with tag $tag")
        super.cancelAll(tag)
    }

    override fun <T : Any?> add(request: Request<T>?): Request<T> {
        println("Note: FakeRequestQueue is used")
        System.out.println("New request " + request?.url + " is added with priority " + request?.priority)
        try {
            if (request?.body == null) {
                println("body is null")
            } else {
                println("Body:" + String(request.body))
            }
        } catch (e: AuthFailureError) {
            // cannot do anything
        }

        return super.add<T>(request)
    }
}