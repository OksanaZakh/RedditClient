package com.example.ozakharc.redditclient.utils

import com.example.ozakharc.redditclient.model.ItemComment

fun List<ItemComment>.getStringComments(): String {
    var result = ""
    if (this != null) {
        for (comment in this) {
            result += "$comment \n"
        }
    }
    return result
}
