package com.example.openlib.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openlib.MainApplication
import com.example.openlib.data.local.Book
import com.example.openlib.data.local.test_Room.BookRepository
import com.example.openlib.data.remote.NetworkResponse
import com.example.openlib.data.remote.RetrofitInstance
import com.example.openlib.data.remote.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class BookViewModel :ViewModel(){
    // api de livres
    private val bookAPI = RetrofitInstance.bookAPI

    val bookDao = MainApplication.bookDatabase.getBookDao()

    // liveData exposé á l'UI , encapsuler dans le NetworkResponse
    private val _bookResult = MutableLiveData<NetworkResponse<List<Book>>>()
    val bookResult:LiveData<NetworkResponse<List<Book>>> = _bookResult

     val allBooks :LiveData<List<Book>> = BookRepository.getAllBooks()
// private var _selectedBook : Book? = null ;
   // val selectbook : Book? get() = _selectedBook
    var selectedBook: Book? by mutableStateOf(null)

/*
 init {
        // Initialisation avec le livre "Kotlin" si la base de données est vide
        viewModelScope.launch(Dispatchers.IO) {
          val booksCount = BookRepository.getCount() // Méthode pour compter les livres dans Room
            if (booksCount == 0) {
           searchBooks("Kotlin") // Rechercher "Kotlin" depuis l'API pour remplir la base
            }
        }
    }

 */

    private val _favoriteBooks = MutableLiveData<List<Book>>(emptyList()) // Initialisation à une liste vide
    val favoriteBook: LiveData<List<Book>> = _favoriteBooks

fun updateSelectedBook(book: Book){
    this.selectedBook = book
}
    // Add to favorits
    fun addBookToFavorites(book: Book){
        val currentFavorites = _favoriteBooks.value?.toMutableList()?: mutableListOf()
        if(!currentFavorites.contains(book)) {
            _favoriteBooks.value = currentFavorites + book
        }
    }

    // remove from the favorite list
    fun removeBookFromFavorites(book: Book){
        val currentFavorites = _favoriteBooks.value?.toMutableList() ?: mutableListOf()
           if(currentFavorites.contains(book)) {
               _favoriteBooks.value = currentFavorites.filter { it.id != book.id }
           }
    }




    // function pour rechercher les livre avec un paramétre de requete ("kotlin")
     fun searchBooks(query:String) {


        viewModelScope.launch {
            _bookResult.value = NetworkResponse.Loading
            try {
                // Appel API avec Retrofit
                val response: Response<SearchResponse> =bookAPI.searchbooks(query)
                if (response.isSuccessful()) {


                    // on récupree les resultats
                    response.body()?.let {searchResponse ->




                        val books = searchResponse.docs
                        Log.d("books ", books.toString())

                          saveBooksToDatatbase(books)

                        _bookResult.value = NetworkResponse.Success(books)
                    } ?: run {
                        _bookResult.value = NetworkResponse.Error("No Books found")
                    }
                } else {
                    // Si la réponse n'est pas un succès (par exemple, erreur HTTP)
                    _bookResult.value = NetworkResponse.Error("Error server: ${response.code()}")
                }
            } catch (e: Exception) {
                // Si une exception est levée (par exemple, une erreur réseau)
                _bookResult.value = NetworkResponse.Error("Network Error: ${e.message}")
            }
        }
    }

    suspend   fun saveBooksToDatatbase(books:List<Book>){
        withContext(Dispatchers.IO) {
             bookDao.deleteAllBooks()
             bookDao.addBooks(books)
         }
     }


}