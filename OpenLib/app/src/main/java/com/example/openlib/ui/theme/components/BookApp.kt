package com.example.openlib.ui.theme.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.openlib.data.local.Book
import com.example.openlib.data.remote.NetworkResponse
import com.example.openlib.viewModel.BookViewModel
import kotlinx.coroutines.launch

@Composable
fun BookPage(viewModel: BookViewModel) {
    var query by rememberSaveable { mutableStateOf("") }

    val bookResult = viewModel.bookResult.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

   val allBooks = viewModel.allBooks.observeAsState(initial = emptyList() ) // Observer les livres en local

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {

            OutlinedTextField(
                modifier = Modifier.weight(1f),  // Pour étendre la zone de saisie
                value = query,
                onValueChange = {
                    query = it
                },
                label = { Text("Rechercher un livre") }
            )

            IconButton(onClick = {

                    viewModel.searchBooks(query) // Lancer la recherche

                keyboardController?.hide() // Masquer le clavier après la recherche
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Rechercher")
            }
        }
 //    if(allBooks.value.isNullOrEmpty()) {


         // Gestion des états de l'API (à compléter si nécessaire)
         // Gestion de l'état de la requête API avec NetworkResponse
         when (val result = bookResult.value) {
             is NetworkResponse.Loading -> {
                 CircularProgressIndicator() // Affichage de chargement
             }

             is NetworkResponse.Success -> {
                 // Affichage des détails des livres
                 BookList(books = result.data)
             }

             is NetworkResponse.Error -> {
                 // Affichage du message d'erreur
                 Text(text = result.message, color = Color.Red)
             }

             null -> {
                 BookList(books = allBooks.value)
             }
         }
   //  }else{
         // Afficher les livres qui sont dans le room
     //    BookList(books = allBooks.value)
    // }
    }
}

@Composable
fun BookList(books: List<Book>) {
    LazyColumn {
        items(books) { book ->
            BookItem(book = book)
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {

        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = book.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = book.author.toString(), fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            book.coverUrl?.let {
                AsyncImage(
                    model = "https://covers.openlibrary.org/b/id/$it-L.jpg",
                    contentDescription = "Image de couverture",
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = book.description ?: "Pas de description disponible", fontSize = 14.sp)
        }
    }
}
