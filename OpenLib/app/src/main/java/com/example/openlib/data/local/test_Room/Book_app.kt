package com.example.openlib.data.local.test_Room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.openlib.data.local.Book

/**
@Composable
fun BookApp_test(viewmodelTest: BookViewModeltest){

    val bookList by viewmodelTest.allBooks.observeAsState(initial = emptyList() )

    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {


    OutlinedTextField(value = title,
        onValueChange = {title = it},
        label = {Text("Book 's title ")},
        modifier = Modifier.fillMaxWidth())

    Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(value = author,
            onValueChange = {author = it},
            label = {Text("Book 's author ")},
            modifier = Modifier.fillMaxWidth())


        Button(onClick = {
            viewmodelTest.addBook(Book(title = title, author = author, coverUrl = "", description = "livre pour test"))
            title = ""
            author =""
        }) {
           Text(text = "add a book ")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(bookList) { book ->
               BookItem(book = book, onDelete = {viewmodelTest.deleteBook(book.id)})
           }
        }

}
}
        */