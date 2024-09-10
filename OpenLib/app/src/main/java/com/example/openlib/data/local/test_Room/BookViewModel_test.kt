package com.example.openlib.data.local.test_Room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openlib.MainApplication
import com.example.openlib.data.local.Book

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModeltest : ViewModel() {

    // Accès direct au DAO via la base de données dans MainApplication
    private val bookDao = MainApplication.bookDatabase.getBookDao()

    // LiveData qui expose la liste des livres
    val allBooks: LiveData<List<Book>> = bookDao.getAllBooks()

    // Ajouter un livre
    fun addBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            bookDao.addBook(book)
        }
    }

    // Supprimer un livre par ID
    fun deleteBook(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            bookDao.deleteBook(id)
        }
    }

    // Mettre à jour un livre
    fun updateBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            bookDao.updateBook(book)
        }
    }
}
