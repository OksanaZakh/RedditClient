package com.example.ozakharc.redditclient.detailed.binding

import android.databinding.BindingAdapter
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import com.example.ozakharc.redditclient.App
import com.squareup.picasso.Picasso

@BindingAdapter("app:url")
fun ImageView.loadImage(url: String) {
    if (URLUtil.isValidUrl(url)) {
        Picasso.with(App.instance)
                .load(url)
                .into(this)
    } else visibility = View.GONE
}