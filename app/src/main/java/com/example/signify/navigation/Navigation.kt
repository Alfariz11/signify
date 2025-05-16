package com.example.signify.navigation

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.signify.screens.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import viewmodel.AuthViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    startDestination: String = "welcome"
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                authViewModel.firebaseAuthWithGoogle(idToken) { success, message ->
                    if (success) {
                        navController.navigate("home") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    } else {
                        Log.e("GOOGLE_AUTH", message ?: "Unknown error")
                        Toast.makeText(context, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: ApiException) {
            Log.e("GOOGLE_AUTH", "Google sign-in failed", e)
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("welcome") { WelcomeScreen(navController, launcher) }
        composable("signin") { SignInScreen(navController, authViewModel) }
        composable("signup") { SignUpScreen(navController, authViewModel) }
        composable("forgotpassword") { ForgotPasswordScreen(navController, authViewModel) }
        composable("home") { HomeScreen(navController, authViewModel) }
        composable("leaderboard") { LeaderboardScreen(navController, authViewModel) }
        composable("kamus") { KamusScreen(navController, authViewModel) }
        composable("rapidgame") { RapidGameScreen(navController, authViewModel) }
        composable("profile") { ProfileScreen(navController, authViewModel) }
    }
}

