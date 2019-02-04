package com.panatchai.abcnews.ui.binding

import android.databinding.BindingAdapter
import android.view.View
import android.widget.TextView
import com.panatchai.abcnews.util.DependencyModule
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("isVisible")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("formatDate")
    fun formatDate(textView: TextView, date: String) {
        val currentLocale = DependencyModule.getInstance(textView.context).provideLocale()
        val parserDf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", currentLocale)
        parserDf.isLenient = false // Strictly match the pattern
        val displayDf = SimpleDateFormat("MMM d, yyyy h:mm a", currentLocale)

        textView.text = try {
            val parse = parserDf.parse(date)
            displayDf.format(parse)
        } catch (e: ParseException) {
            date
        }
    }
}
