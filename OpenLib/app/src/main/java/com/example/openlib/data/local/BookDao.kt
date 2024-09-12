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

    @Query("SELECT * FROM Book")
    fun getAllBooks(): LiveData<List<Book>> // Récupération des livres locaux en LiveData (asynchrone)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addBook(book: Book)  // Ajout d'un livre (opération suspendue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBooks(books: List<Book>)  // Ajout de plusieurs livres (opération suspendue)

    @Update
    fun updateBook(book: Book)  // Mise à jour d'un livre (opération suspendue)

    @Query("DELETE FROM Book WHERE id = :id")
     fun deleteBook(id: Int)  // Suppression d'un livre par ID (opération suspendue)

    @Query("DELETE FROM Book")
     fun deleteAllBooks()  // Suppression de tous les livres (opération suspendue)

    @Query("SELECT * FROM Book WHERE id = :id")
     fun getBookById(id: Int): Book  // Récupérer un livre par son ID (opération suspendue)

    @Query("SELECT * FROM Book WHERE title LIKE :title")
    fun searchBooksByTitle(title: String): LiveData<List<Book>>  // Recherche de livres par titre (LiveData)

    @Query("SELECT * FROM Book WHERE author LIKE :author")
    fun searchBooksByAuthor(author: String): LiveData<List<Book>>  // Recherche de livres par auteur (LiveData)

    @Query("SELECT EXISTS(SELECT 1 FROM Book WHERE id = :id)")
   fun isBookExists(id: Int): Boolean  // Vérifie si un livre existe (opération suspendue)

    @Query("SELECT COUNT(*) FROM Book")
   fun getBooksCount(): Int  // Compte les livres dans la base (opération suspendue)
}
