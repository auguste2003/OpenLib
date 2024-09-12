package com.example.openlib

import android.app.Application
import androidx.room.Room
import com.example.openlib.data.local.BookDatabase

class MainApplication:Application() {

    companion object{
        lateinit var bookDatabase: BookDatabase
    }

    override fun onCreate() { // build the database
        super.onCreate()
        bookDatabase = Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java,
            BookDatabase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}