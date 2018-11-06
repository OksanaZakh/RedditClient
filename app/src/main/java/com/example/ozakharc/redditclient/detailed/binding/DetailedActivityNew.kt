package com.example.ozakharc.redditclient.detailed.binding

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ozakharc.redditclient.R
import com.example.ozakharc.redditclient.api.NewsItem
import com.example.ozakharc.redditclient.databinding.ActivityDetailedNewBinding
import com.example.ozakharc.redditclient.utils.Constants
import com.squareup.picasso.Picasso
import android.databinding.BindingAdapter
import android.widget.ImageView


class DetailedActivityNew : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailedNewBinding>(this,R.layout.activity_detailed_new)
        binding.viewModel= ViewModelProviders.of(this, CustomViewModelFactory(
                intent.extras.get(Constants.NEWS_ITEM) as NewsItem))
                .get(DetailedViewModel::class.java)
    }

//    @BindingAdapter("app:url")
//    fun loadImage(view: ImageView, url: String) {
//        Picasso.with(view.getContext())
//                .load(url)
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .into(view)
//    }
}

