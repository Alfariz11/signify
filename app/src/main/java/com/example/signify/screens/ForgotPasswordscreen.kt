package com.example.signify.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signify.R
import viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(navController: NavController, viewModel: AuthViewModel) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
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
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0B3D5B)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Judul
        Text(
            text = "Masukkan email Anda untuk mengatur ulang password",
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

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol Kirim Link Reset
        Button(
            onClick = {
                viewModel.sendResetPasswordEmail(email,
                    onSuccess = {
                        Toast.makeText(context, "Cek email Anda untuk reset password", Toast.LENGTH_LONG).show()
                        navController.navigate("welcome") {
                            popUpTo("forgotpassword") { inclusive = true }
                        }
                    },
                    onError = {
                        errorMessage = it
                    }
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF68D39C)),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text("Kirim Link Reset", color = Color.White)
        }

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Teks navigasi ke Sign In
        TextButton(onClick = { navController.navigate("signin") }) {
            Text("Kembali ke Sign In", fontSize = 14.sp, color = Color(0xFF68D39C))
        }
    }
}
