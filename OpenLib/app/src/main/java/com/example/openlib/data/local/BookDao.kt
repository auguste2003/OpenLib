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

    @Query("SELECT * FROM BOOK_TABLE ")
    fun getAllBooks():LiveData<List<Book>> // get all books

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend  fun addBook(book: Book) // add new Book

    @Query("Delete FROM book_table Where id = :id") // delete a book
   suspend fun deleteBook(id:Int)

    @Update
    suspend  fun updateBook(book: Book)

    @Query("SELECT * FROM BOOK_TABLE WHERE id = :id") // Get the Book by the id
    suspend fun getBookById(id: Int):Book

    @Query("SELECT * FROM BOOK_TABLE WHERE title LIKE :title") // get the books by
    suspend fun searchBooksByTitle(title: String):LiveData<List<Book>>

    @Query("SELECT * FROM book_table WHERE author LIKE :author ") // get the books by the authors
    suspend fun searchBooksByAuthor(author:String):LiveData<List<Book>>

    @Query("DELETE  FROM book_table") // delete all books
    suspend fun deleteAllBooks()

    @Query("SELECT EXISTS(SELECT 1 FROM book_table WHERE id = :id)")
    suspend fun isBookExists(id: Int)






}