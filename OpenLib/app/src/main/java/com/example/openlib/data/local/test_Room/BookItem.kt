package com.example.openlib.data.local.test_Room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Delete
import com.example.openlib.data.local.Book


@Composable
fun BookItem(book: Book, onDelete: () -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column (Modifier.padding(16.dp)){
            Text(text = "Title : ${book.title}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Author : ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onDelete) {
                Text(text = "Delete")
            }
        }
    }
}