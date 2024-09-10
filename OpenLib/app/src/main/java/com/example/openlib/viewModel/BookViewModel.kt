package com.example.openlib.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openlib.data.local.Book
import com.example.openlib.data.remote.NetworkResponse
import com.example.openlib.data.remote.RetrofitInstance
import com.example.openlib.data.remote.SearchResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class BookViewModel :ViewModel(){
    // api de livres
    private val bookAPI = RetrofitInstance.bookAPI

    // liveData exposé á l'UI , encapsuler dans le NetworkResponse
    private val _bookResult = MutableLiveData<NetworkResponse<List<Book>>>()
    val bookResult:LiveData<NetworkResponse<List<Book>>> = _bookResult

    // function pour rechercher les livre avec un paramétre de requete ("kotlin")
    fun searchBooks(query:String) {

        viewModelScope.launch {
            _bookResult.value = NetworkResponse.Loading
            try {
                // Appel API avec Retrofit
                val response: Response<SearchResponse> =bookAPI.searchbooks(query)
                if (response.isSuccessful()) {
                    // on récupree les resultats
                    response.body()?.let {
                        _bookResult.value = NetworkResponse.Success(it.docs)
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


}