package com.example.openlib.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.openlib.data.local.Book
import com.example.openlib.viewModel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favorite(navController: NavController,viewModel: BookViewModel){

 Scaffold(
     topBar = {
         TopAppBar(
             title = { Text(text = "Favorit Books") },
             navigationIcon = {
                 IconButton(onClick = {navController.popBackStack() }) {
                     Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="Back" )
                 }
             }
         )
     },
     bottomBar = {
         BottomNavigationBar(navController)
     },
     content = {paddingvalues->
         val favoriteBooks by viewModel.favoriteBook.observeAsState(emptyList())
         LazyColumn(modifier = Modifier.padding(paddingvalues)) {
             items(favoriteBooks){book ->
                 FavoriteBookItem(book,viewModel,navController);

             }
         }
     }
 )


}
@Composable
fun FavoriteBookItem(book: Book, viewModel: BookViewModel,navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
            viewModel.updateSelectedBook(book)
            navController.navigate("bookDetail")
        },  // Bordures arrondies
        elevation = CardDefaults.cardElevation(8.dp)  // Ajout d'une ombre douce
    ) {
        val authors = if (book.author.isNotEmpty()) {
            book.author.joinToString(", ")
        } else {
            "Unknown Author"
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = book.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = authors,
                fontSize = 16.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            book.coverUrl?.let {
                AsyncImage(
                    model = "https://covers.openlibrary.org/b/id/$it-L.jpg",
                    contentDescription = "Cover image",
                    modifier = Modifier
                        .size(150.dp)  // Agrandissement de l'image
                        .clip(RoundedCornerShape(8.dp))  // Bordures arrondies de l'image
                        .background(Color.LightGray)  // Fond neutre derrière l'image
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.description ?: "No description available",
                fontSize = 14.sp,
                color = Color.DarkGray,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.removeBookFromFavorites(book) },
                modifier = Modifier.align(Alignment.End),  // Bouton aligné à droite
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))  // Couleur d'accent
            ) {
                Text(text = "Remove from Favorites", color = Color.White)
            }
        }
    }
}
