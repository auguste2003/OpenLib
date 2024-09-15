package com.example.openlib.ui.theme.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.openlib.data.local.Book
import com.example.openlib.data.remote.NetworkResponse
import com.example.openlib.viewModel.BookViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(viewModel: BookViewModel,navController: NavController) {
    var query by rememberSaveable { mutableStateOf("") }

    val bookResult = viewModel.bookResult.observeAsState()
    val allBooks = viewModel.allBooks.observeAsState(initial = emptyList())

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                   // Text("Book Library", fontWeight = FontWeight.Bold, fontSize = 20.sp)  // Titre stylisé
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "List of books",
                       // tint = Color.White  // Icône blanche pour cohérence avec le top bar
                    )
                },
             //   containerColor = Color(0xFF6200EE),  // Couleur d'accent
                actions = {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text("Search a book") },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .clip(RoundedCornerShape(8.dp))  // Bordures arrondies
                            .background(Color.White)  // Fond blanc pour plus de contraste
                    )
                    IconButton(onClick = {
                        viewModel.searchBooks(query)
                        keyboardController?.hide()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                         //   tint = Color.White  // Icône blanche pour cohérence avec le top bar
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                },
                containerColor = Color(0xFF6200EE),  // Couleur d'accent
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)  // Ajout d'élévation pour ombre
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Scroll to Top")
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)  // BottomBar avec le nouveau design
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Première section : LazyRow pour les livres populaires
            Text(text = "Popular Books", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(16.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(allBooks.value.take(5)) { book ->  // Affiche les 5 premiers livres
                    BookItemHorizontal(viewModel,navController,book = book)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Deuxième section : LazyColumn pour afficher les livres
            Text(text = "All Books", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(16.dp))
            LazyColumn(
                state = scrollState,
                contentPadding = PaddingValues(bottom = 80.dp),  // Marge pour le FAB
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Si des livres sont disponibles en local (dans Room)
                if (allBooks.value.isNotEmpty()) {
                    items(allBooks.value) { book ->
                        BookCard(book = book, viewModel = viewModel,navController)
                    }
                } else {
                    // Gérer les résultats provenant de l'API
                    when (val result = bookResult.value) {
                        is NetworkResponse.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        is NetworkResponse.Success -> {
                            items(result.data) { book ->
                                BookCard(book = book, viewModel = viewModel,navController)
                            }
                        }

                        is NetworkResponse.Error -> {
                            item {
                                Text(
                                    text = "Error: ${result.message}",
                                    color = Color.Red,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        null -> {
                            item {
                                Text(
                                    text = "No books found",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BookItemHorizontal(viewModel: BookViewModel,navController: NavController,book: Book) {
    Card(
        modifier = Modifier
            .width(160.dp)  // Légèrement plus large pour un meilleur rendu
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                viewModel.updateSelectedBook(book)
                navController.navigate("bookDetail")
            },  // Bordures arrondies pour un design doux
        elevation = CardDefaults.cardElevation(6.dp)  // Ombre plus douce
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            book.coverUrl?.let {
                AsyncImage(
                    model = "https://covers.openlibrary.org/b/id/$it-L.jpg",
                    contentDescription = "Cover image",
                    modifier = Modifier
                        .size(120.dp)  // Augmentation de la taille de l'image
                        .clip(RoundedCornerShape(10.dp))  // Bordures arrondies pour l'image
                        .background(Color.LightGray)  // Fond neutre
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                textAlign = TextAlign.Center  // Centrage du titre
            )
        }
    }
}

@Composable
fun BookCard(book: Book, viewModel: BookViewModel,navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                       viewModel.updateSelectedBook(book)
                navController.navigate("bookDetail")
            },  // Bordures plus arrondies pour un effet moderne
        elevation = CardDefaults.cardElevation(10.dp)  // Plus d'élévation pour mettre en valeur la carte
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically  // Aligne les éléments au centre verticalement
            ) {
                book.coverUrl?.let {
                    AsyncImage(
                        model = "https://covers.openlibrary.org/b/id/$it-L.jpg",
                        contentDescription = "Cover image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )
                    val authors = if (book.author.isNotEmpty()) {
                        book.author.joinToString(", ")
                    } else {
                        "Unknown authors"
                    }
                    Text(authors, fontSize = 14.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { viewModel.addBookToFavorites(book) },
                modifier = Modifier.align(Alignment.End),  // Bouton aligné à droite
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))  // Couleur d'accent
            ) {
                Text(text = "Add to Favorites", color = Color.White)  // Texte blanc pour contraste
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    var isClicked by remember { mutableStateOf(false)}
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)) // Bordures arrondies sur le haut de la barre
            .background(Color(0xFF6200EE)),  // Couleur d'accent harmonieuse
        tonalElevation = 10.dp  // Légère ombre pour donner un effet de profondeur
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically  // Aligne les icônes au centre verticalement
        ) {
            IconButton(onClick = { isClicked = ! isClicked;
                navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                     // Icônes en blanc pour un bon contraste
                )
            }
            IconButton(onClick = { navController.navigate("favorite") }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorites",
                    tint = Color.White
                )
            }
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }
    }
}

