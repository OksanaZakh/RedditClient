package com.example.ozakharc.redditclient.detailed.binding

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.BindingAdapter
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import com.example.ozakharc.redditclient.App
import com.example.ozakharc.redditclient.api.NewsItem
import com.example.ozakharc.redditclient.model.ItemComment
import com.squareup.picasso.Picasso

class DetailedViewModel(val newsItem: NewsItem): ViewModel() {
    val comments: LiveData<List<ItemComment>> = App.instance?.getAppDb()?.itemCommentDao()!!.getComments(newsItem.permalink)


}

class CustomViewModelFactory(private val test: NewsItem) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailedViewModel(test) as T
    }
}

@BindingAdapter("app:url")
fun ImageView.loadImage(url: String) {
    if(URLUtil.isValidUrl(url)) {
        Picasso.with(App.instance)
                .load(url)
                .into(this)
    }else visibility=View.GONE
}