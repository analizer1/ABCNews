package com.panatchai.abcnews.data.api

import com.android.volley.Response
import com.panatchai.abcnews.data.util.getErrorMessage

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.getErrorMessage())
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccess) {
                val body = response.result
                if (body == null) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body = body)
                }
            } else {
                val errorMsg = response.error.getErrorMessage()
                ApiErrorResponse(errorMsg)
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T
) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()