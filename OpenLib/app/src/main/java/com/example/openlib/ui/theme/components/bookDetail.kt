package com.example.openlib.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.openlib.viewModel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(viewModel: BookViewModel,navController: NavController) {
    val book  = viewModel.selectedBook
    book?.let {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Book Details") },
                 navigationIcon = {
                     IconButton(onClick = {navController.popBackStack() }) {
                         Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="Back" )
                     }
                 }
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Text(
                        text = it.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val authors = if (it.author.isNotEmpty()) {
                        it.author.joinToString(", ")
                    } else {
                        "Unknown Author"
                    }
                    Text(
                        text = authors,
                        fontSize = 18.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    it.coverUrl?.let { coverUrl ->
                        AsyncImage(
                            model = "https://covers.openlibrary.org/b/id/$coverUrl-L.jpg",
                            contentDescription = "Cover image",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = it.description ?: "No description available",
                        fontSize = 16.sp
                    )
                }
            }
        )
    }
}
