package com.example.openlib.data.local.test_Room

import androidx.lifecycle.LiveData
import com.example.openlib.MainApplication
import com.example.openlib.data.local.Book

object BookRepository {

    val bookDao = MainApplication.bookDatabase.getBookDao()
    // LiveData pour observer les données en temps réel
    // val allBooks = bookDao.getAllBooks()

    // Fonction pour ajouter un livre
    suspend fun addBook(book: Book) {
        bookDao.addBook(book)
    }

    // Fonction pour supprimer un livre
    suspend fun deleteBook(id: Int) {
        bookDao.deleteBook(id)
    }

    // Fonction pour mettre à jour un livre
    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }
    suspend fun deleteBooks(){
        bookDao.deleteAllBooks()
    }
    suspend fun getCount():Int{
       return bookDao.getBooksCount()
    }
     fun getAllBooks():LiveData<List<Book>>{
        return bookDao.getAllBooks()
    }
}