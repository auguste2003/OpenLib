package com.example.openlib.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.openlib.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
             //   backgroundColor = Color(0xFF6200EE),
             //   contentColor = Color.White
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Image de profil
            Image(
                painter = painterResource( R.drawable.scheinbild),  // Remplace par ton image
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)  // Image ronde
                    .border(4.dp, Color(0xFF6200EE), CircleShape)  // Bordure violette élégante
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Informations sur le profil
            Text(
                text = "Auguste Atoundem Sonfack ",  // Remplace par ton nom
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Computer Science Student, THM Gießen ",  // Ajoute tes infos ici
                fontSize = 18.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Bouton d'édition de profil (optionnel)
            Button(
                onClick = { /* Action pour éditer le profil */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text(text = "Edit Profile", color = Color.White)
            }
        }
    }
}

