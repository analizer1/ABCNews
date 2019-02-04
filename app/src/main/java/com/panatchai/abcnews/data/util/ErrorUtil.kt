package com.panatchai.abcnews.data.util

/**
 * Safely get error message.
 */
fun Throwable?.getErrorMessage(): String {
    var errorMessage = this?.message ?: this?.localizedMessage ?: ""
    if (errorMessage.isEmpty()) {
        errorMessage = "unknown error"
    }
    return errorMessage
}