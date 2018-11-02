package com.example.ozakharc.redditclient

import android.app.Application
import android.arch.persistence.room.Room
import com.example.ozakharc.redditclient.model.DataBase

class App : Application() {

    private lateinit var appDb: DataBase

    override fun onCreate() {
        super.onCreate()
        instance = this
        createDb()
    }

    companion object {
        var instance: App? = null
            private set

    }

    private fun createDb() {
//        appDb = Room.databaseBuilder(applicationContext,
//                DataBase::class.java, "database")
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
//                .build()
    }

    fun getAppDb(): DataBase {
        if (!appDb.isOpen) createDb()
        return appDb
    }
}
