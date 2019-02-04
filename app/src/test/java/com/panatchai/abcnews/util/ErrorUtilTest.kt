package com.panatchai.abcnews.util

import com.panatchai.abcnews.data.util.getErrorMessage
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

@Suppress("TestFunctionName")
class ErrorUtilTest {

    @Test
    fun GivenErrorMessage_ReturnErrorMessage() {
        val t = Throwable("error")
        assertThat(t.getErrorMessage(), `is`("error"))
    }

    @Test
    fun GivenNoErrorMessage_ReturnUnknownErrorMessage() {
        val t = Throwable()
        assertThat(t.getErrorMessage(), `is`("unknown error"))
    }
}