package com.example.signify.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import viewmodel.AuthViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val user = viewModel.currentUser.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        user?.let {
            Text("Welcome, ${it.displayName ?: it.email}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.signOut()
                navController.navigate("welcome") {
                    popUpTo("home") { inclusive = true }
                }
            }) {
                Text("Sign Out")
            }
        } ?: run {
            Text("No user logged in", fontSize = 18.sp)
        }
    }
}

