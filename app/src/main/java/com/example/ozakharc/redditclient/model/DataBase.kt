package com.example.ozakharc.redditclient.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [ItemComment::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun itemCommentDao(): ItemCommentDao
}