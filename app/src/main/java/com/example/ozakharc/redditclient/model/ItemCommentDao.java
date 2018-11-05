package com.example.ozakharc.redditclient.model;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public abstract class ItemCommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ItemComment... items);

    @Query("SELECT * FROM itemcomment WHERE permalink=:permalink")
    public abstract LiveData<List<ItemComment>> search(final String permalink);

    @Query("DELETE FROM itemcomment")
    public abstract void deleteAll();
}
