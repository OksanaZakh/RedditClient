package com.example.ozakharc.redditclient.api

import java.io.Serializable


data class NewsItem(var after: String,
                    var title: String,
                    var thumbnail: String,
                    var numComments: Int,
                    var author: String,
                    var createdUtc: Long,
                    var selftext: String,
                    var photoUrl: String,
                    var url: String,
                    var permalink: String
) : Serializable {

    constructor() : this("", "", "", 0, "", 0L,
            "", "", "", "")
}

