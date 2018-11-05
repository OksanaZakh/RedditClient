//package com.example.ozakharc.redditclient.model
//
//import android.arch.lifecycle.LiveData
//import android.arch.persistence.room.Dao
//import android.arch.persistence.room.Insert
//import android.arch.persistence.room.OnConflictStrategy.REPLACE
//import android.arch.persistence.room.Query
//
//@Dao
//interface ItemCommentDao {
//
//    @Insert(onConflict = REPLACE)
//    fun insert(itemComment: ItemComment)
//
//    @Insert(onConflict = REPLACE)
//    fun insertAll(comments: List<ItemComment>)
//
//    @Query("DELETE from itemcomment")
//    fun deleteAll()
//
//    @Query("SELECT * from itemcomment WHERE permalink = :permalink")
//    fun search(permalink: String): LiveData<List<ItemComment>>
//
//}