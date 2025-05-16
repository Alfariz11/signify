package com.example.signify.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.signify.component.BottomNavBar
import viewmodel.AuthViewModel

@Composable
fun LeaderboardScreen(navController: NavController, authViewModel: AuthViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Konten utama screen Kamus di sini
        Text(
            text = "Ini Leader Screen",
            modifier = Modifier.align(Alignment.Center)
        )

        // BottomNavBar tetap di bawah
        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
        )
    }
}
