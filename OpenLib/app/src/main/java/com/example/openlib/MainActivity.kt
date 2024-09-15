package com.example.openlib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController

import com.example.openlib.data.local.test_Room.BookViewModeltest
import com.example.openlib.ui.theme.OpenLibTheme
import com.example.openlib.ui.theme.components.App
import com.example.openlib.ui.theme.components.BookPage
import com.example.openlib.ui.theme.components.HomePage
import com.example.openlib.viewModel.BookViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
       //  val viewModeltestroom = ViewModelProvider(this)[BookViewModeltest::class.java]
      val viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            OpenLibTheme {
                val navController = rememberNavController()
            //    BookPage(viewModel )
             //  HomePage(viewModel,navController)
                 App()
            }




        }
    }
}

