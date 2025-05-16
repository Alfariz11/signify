package com.example.signify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.signify.navigation.AppNavHost
import com.google.firebase.FirebaseApp
import viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            val navController = rememberNavController()
            val viewModel: AuthViewModel = viewModel()
            val currentUser by viewModel.currentUser.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()

            if (isLoading) {
                // Loading screen sederhana
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val startDestination = if (currentUser == null) "welcome" else "home"
                AppNavHost(navController = navController, authViewModel = viewModel, startDestination = startDestination)
            }
        }
    }
}
