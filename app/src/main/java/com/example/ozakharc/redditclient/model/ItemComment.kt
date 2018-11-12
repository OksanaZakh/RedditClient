package com.example.ozakharc.redditclient.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ItemComment(@PrimaryKey(autoGenerate = false) var id: String,
                       var comment: String,
                       var permalink: String) {
    constructor() : this("", "", "")

    override fun toString(): String {
        return comment
    }
}