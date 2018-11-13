package com.example.ozakharc.redditclient.detailed.binding

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ozakharc.redditclient.R
import com.example.ozakharc.redditclient.api.NewsItem
import com.example.ozakharc.redditclient.databinding.ActivityDetailedNewBinding
import com.example.ozakharc.redditclient.utils.Constants
import com.squareup.picasso.Picasso
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import com.example.ozakharc.redditclient.model.ItemComment
import com.example.ozakharc.redditclient.utils.getStringComments


class DetailedActivityNew : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this, CustomViewModelFactory(
                intent.extras.get(Constants.NEWS_ITEM) as NewsItem))
                .get(DetailedViewModel::class.java)

        viewModel.getCommentsFromDB().observe(this, Observer<List<ItemComment>> { comments ->
            findViewById<TextView>(R.id.tvComments).text = comments?.getStringComments()
        })

        val binding = DataBindingUtil.setContentView<ActivityDetailedNewBinding>(this, R.layout.activity_detailed_new)
        binding.viewModel = viewModel
        binding.activity = this
    }

    fun onLinkClicked(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    fun showDialog(imageUrl: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.image_dialog)
        val image = dialog.findViewById(R.id.image) as ImageView
        Picasso.with(this).load(imageUrl).fit().centerCrop().placeholder(R.drawable.ic_launcher_foreground).into(image)
        image.setOnClickListener { v -> dialog.dismiss() }
        dialog.show()
    }
}

