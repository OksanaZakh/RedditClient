package com.example.ozakharc.redditclient.detailed.binding

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.WorkerThread
import com.example.ozakharc.redditclient.App
import com.example.ozakharc.redditclient.api.NewsItem
import com.example.ozakharc.redditclient.model.ItemComment

class DetailedViewModel(val newsItem: NewsItem) : ViewModel() {

    @WorkerThread
    fun getCommentsFromDB(): LiveData<List<ItemComment>> {
        return App.instance?.getAppDb()?.itemCommentDao()!!.getComments(newsItem.permalink)
    }
}

class CustomViewModelFactory(private val test: NewsItem) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailedViewModel(test) as T
    }
}

