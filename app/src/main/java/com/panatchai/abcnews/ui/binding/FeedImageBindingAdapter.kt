package com.panatchai.abcnews.ui.binding

import android.databinding.BindingAdapter
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.panatchai.abcnews.ui.model.NewsFeed

/**
 * Custom image binding.
 */
class FeedImageBindingAdapter constructor(
    private val imageLoader: ImageLoader
) {
    @BindingAdapter("imageUrl", "position")
    fun bindImage(imageView: NetworkImageView, newsFeed: NewsFeed?, position: Int?) {
        newsFeed?.let { feed ->
            // only first feed is large
            val imageUrl = if (position?.equals(0) == true) {
                feed.imgUrl
            } else {
                feed.thumbnail
            }

            imageLoader.get(
                imageUrl,
                ImageLoader.getImageListener(
                    imageView,
                    android.R.drawable.ic_dialog_alert,
                    android.R.drawable.ic_dialog_alert
                )
            )
            imageView.setImageUrl(imageUrl, imageLoader)
        }
    }
}