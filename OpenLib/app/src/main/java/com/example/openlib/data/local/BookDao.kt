package com.example.openlib.data.local

import android.service.quicksettings.Tile
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Configure the DAO to get acces to the DB
 */
@Dao
interface BookDao {

    @Query("SELECT * FROM Book ")
    fun getAllBooks():LiveData<List<Book>> // get all books

    @Insert
    fun addBook(book: Book) // add new Book

    @Query("Delete FROM Book Where id = :id") // delete a book
    fun deleteBook(id:Int)

    @Update
      fun updateBook(book: Book)

    @Query("SELECT * FROM Book WHERE id = :id") // Get the Book by the id
     fun getBookById(id: Int):Book

    @Query("SELECT * FROM Book WHERE title LIKE :title") // get the books by
     fun searchBooksByTitle(title: String):LiveData<List<Book>>

    @Query("SELECT * FROM Book WHERE author LIKE :author ") // get the books by the authors
     fun searchBooksByAuthor(author:String):LiveData<List<Book>>

    @Query("DELETE  FROM Book") // delete all books
     fun deleteAllBooks()

    @Query("SELECT EXISTS(SELECT 1 FROM Book WHERE id = :id)")
     fun isBookExists(id: Int): Boolean






}