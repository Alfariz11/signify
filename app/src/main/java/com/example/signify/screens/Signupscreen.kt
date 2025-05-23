package com.example.signify.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signify.R
import viewmodel.AuthViewModel

@Composable
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Logo dan teks Signify
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.signify_logo),
                contentDescription = "Logo Signify",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Signify",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0B3D5B)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Judul
        Text(
            text = "Buat akun baru!",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF68D39C)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color(0xFF68D39C)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color(0xFF68D39C),
                focusedLabelColor = Color(0xFF68D39C)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color(0xFF68D39C)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color(0xFF68D39C),
                focusedLabelColor = Color(0xFF68D39C)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Re-Password", color = Color(0xFF68D39C)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedLabelColor = Color(0xFF68D39C),
                focusedLabelColor = Color(0xFF68D39C)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Sign Up
        Button(
            onClick = {
                if (password == confirmPassword) {
                    viewModel.signUp(email, password,
                        onSuccess = { navController.navigate("home") },
                        onError = { errorMessage = it })
                } else {
                    errorMessage = "Passwords do not match"
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF68D39C)),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Sign Up", color = Color.White)
        }

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Teks navigasi ke Sign In
        TextButton(onClick = { navController.navigate("signin") }) {
            Text("Already have an account? Sign In", fontSize = 14.sp, color = Color(0xFF68D39C))
        }
    }
}

