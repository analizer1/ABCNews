package com.panatchai.abcnews.data.model

import com.panatchai.abcnews.data.model.Status.LOADING
import com.panatchai.abcnews.data.model.Status.SUCCESS
import com.panatchai.abcnews.data.model.Status.ERROR

/**
 * A generic class that holds a value with its loading status.
 * @param <T> resource type.
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
