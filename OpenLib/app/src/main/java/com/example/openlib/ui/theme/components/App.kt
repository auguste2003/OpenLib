package com.example.openlib.ui.theme.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable // Import de la fonction `composable`
import androidx.navigation.compose.rememberNavController
import com.example.openlib.viewModel.BookViewModel

// NavGraph

fun NavGraphBuilder.navigationGraph(navController: NavController){
   composable("favorite"){
      val bookViewModel : BookViewModel = viewModel()
      Favorite(navController,bookViewModel)
   }
   composable("home"){
      val bookViewModel: BookViewModel = viewModel()
      HomePage(bookViewModel,navController)
   }
   composable("Settings"){

      ProfileScreen(navController)
   }
}

// navHost 
@Composable
fun App(){
   val navController = rememberNavController()
   val bookViewModel : BookViewModel = viewModel()
   // NavHost 
   NavHost(navController = navController, startDestination = "home"){
      composable("favorite"){

         Favorite(navController,bookViewModel)
      }
      composable("home"){

         HomePage(bookViewModel,navController)
      }
      composable("bookDetail"){

         BookDetailScreen(bookViewModel,navController)
      }
      composable("Settings"){

         ProfileScreen(navController)
      }
   }

   }