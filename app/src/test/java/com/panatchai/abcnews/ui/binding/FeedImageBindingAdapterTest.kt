package com.panatchai.abcnews.ui.binding

import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.panatchai.abcnews.data.util.RequestUtil
import com.panatchai.abcnews.ui.model.NewsFeed
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Suppress("TestFunctionName", "DEPRECATION")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class FeedImageBindingAdapterTest {

    @Test
    fun WhenBindFirstFeed_ThenBindLargeImage() {
        val im = NetworkImageView(RuntimeEnvironment.application)
        val imageView = spy(im)
        val feed1 = NewsFeed("title1", "date1", "thumb1", "img1")
        val feed2 = NewsFeed("title2", "date2", "thumb2", "img2")
        val imageLoader = mock<ImageLoader>()
        val adapter = FeedImageBindingAdapter(imageLoader)

        val spyFeed1 = spy(feed1)
        adapter.bindImage(imageView, spyFeed1, 0)

        verify(spyFeed1).imgUrl
        verify(spyFeed1, never()).thumbnail

        reset(imageView)
        val spyFeed2 = spy(feed2)
        adapter.bindImage(imageView, spyFeed2, 1)
        verify(spyFeed2).thumbnail
        verify(spyFeed2, never()).imgUrl
    }

    @Test
    fun GivenEmptyImageLink_ThenSetDefault() {
        val im = NetworkImageView(RuntimeEnvironment.application)
        val imageView = spy(im)
        val feed1 = NewsFeed("title1", "date1", "", "")
        val feed2 = NewsFeed("title2", "date2", "", "")
        val context = RuntimeEnvironment.application
        val imageLoader = RequestUtil.getInstance(context).imageLoader
        val adapter = FeedImageBindingAdapter(imageLoader)

        adapter.bindImage(imageView, feed1, 0)

        verify(imageView).setImageResource(android.R.drawable.ic_dialog_alert)

        reset(imageView)
        adapter.bindImage(imageView, feed2, 1)

        verify(imageView).setImageResource(android.R.drawable.ic_dialog_alert)
    }

    @Test
    fun GivenNonImageLink_ThenSetDefault() {
        val im = NetworkImageView(RuntimeEnvironment.application)
        val imageView = spy(im)
        val feed1 = NewsFeed("title1", "date1", "", "http://www.google.com")
        val feed2 = NewsFeed("title2", "date2", "http://www.google.com", "")
        val context = RuntimeEnvironment.application
        val imageLoader = RequestUtil.getInstance(context).imageLoader
        val adapter = FeedImageBindingAdapter(imageLoader)

        adapter.bindImage(imageView, feed1, 0)

        verify(imageView).setImageResource(android.R.drawable.ic_dialog_alert)

        reset(imageView)
        adapter.bindImage(imageView, feed2, 1)

        verify(imageView).setImageResource(android.R.drawable.ic_dialog_alert)
    }
}