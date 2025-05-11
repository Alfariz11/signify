package com.example.signify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

            // Observe auth state changes and navigate accordingly
            if (viewModel.currentUser.value == null) {
                // If no user is logged in, navigate to the welcome screen
                AppNavHost(navController = navController, authViewModel = viewModel, startDestination = "welcome")
            } else {
                // If a user is logged in, navigate to the home screen
                AppNavHost(navController = navController, authViewModel = viewModel, startDestination = "home")
            }
        }
    }
}
