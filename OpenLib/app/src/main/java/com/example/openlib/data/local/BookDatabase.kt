package com.example.openlib.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Book::class], version = 1)
@TypeConverters(Converters::class)
abstract class BookDatabase :RoomDatabase() {

    companion object{
        const val NAME = "Book_DB"
    }
    abstract fun  getBookDao():BookDao // get the DAO
}