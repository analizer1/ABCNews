package com.panatchai.abcnews.ui.binding

import android.view.View
import android.widget.TextView
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Suppress("TestFunctionName", "DEPRECATION")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class BindingAdaptersTest {

    @Test
    fun GivenFalse_ThenVisibilityGone() {
        val v = View(RuntimeEnvironment.application)
        val view = spy(v)

        BindingAdapters.showHide(view, false)

        assertThat(view.visibility, `is`(View.GONE))
    }

    @Test
    fun GivenTrue_ThenVisible() {
        val v = View(RuntimeEnvironment.application)
        val view = spy(v)

        BindingAdapters.showHide(view, true)

        assertThat(view.visibility, `is`(View.VISIBLE))
    }

    @Test
    fun GivenValidDateFormat_ThenSetValidFormattedDate() {
        val tv = TextView(RuntimeEnvironment.application)
        val textView = spy(tv)

        // valid input format is "yyyy-MM-dd hh:mm:ss"
        val validDate = "1984-05-22 10:00:00"
        BindingAdapters.formatDate(textView, validDate)

        val validOutput = "May 22, 1984 10:00 AM"
        verify(textView).text = validOutput
    }

    @Test
    fun GivenNonDateString_ThenSetOriginalString() {
        val tv = TextView(RuntimeEnvironment.application)
        val textView = spy(tv)

        val invalidDate = "This should be a date-time."
        BindingAdapters.formatDate(textView, invalidDate)

        verify(textView).text = invalidDate
    }

    @Test
    fun GivenInValidDateFormat_ThenSetOriginalDateFormat() {
        val tv = TextView(RuntimeEnvironment.application)
        val textView = spy(tv)

        // valid input format is "yyyy-MM-dd hh:mm:ss"
        // given dd-MM-yyyy hh:mm:ss instead
        val invalidDate = "22-05-1984 10:00:00"
        BindingAdapters.formatDate(textView, invalidDate)

        verify(textView).text = invalidDate
    }

    @Test
    fun GivenOnlyDate_ThenSetOriginalDateFormat() {
        val tv = TextView(RuntimeEnvironment.application)
        val textView = spy(tv)

        // valid input format is "yyyy-MM-dd hh:mm:ss"
        val invalidDate = "1984-05-22"
        BindingAdapters.formatDate(textView, invalidDate)

        verify(textView).text = invalidDate
    }

    @Test
    fun GivenOnlyTime_ThenSetOriginalDateFormat() {
        val tv = TextView(RuntimeEnvironment.application)
        val textView = spy(tv)

        // valid input format is "yyyy-MM-dd hh:mm:ss"
        val invalidDate = "10:00:00"
        BindingAdapters.formatDate(textView, invalidDate)

        verify(textView).text = invalidDate
    }
}