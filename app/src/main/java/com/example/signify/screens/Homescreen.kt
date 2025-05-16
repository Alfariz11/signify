package com.example.signify.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signify.R
import com.example.signify.component.BottomNavBar
import viewmodel.AuthViewModel
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val user by viewModel.currentUser.collectAsState()
    val context = LocalContext.current
    var isActivated by remember { mutableStateOf(false) }
    val userName = user?.email?.substringBefore('@') ?: "User"

    // Timer untuk mengembalikan tombol beli setelah 10 menit
    LaunchedEffect(isActivated) {
        if (isActivated) {
            // Menunggu 10 menit sebelum mengaktifkan kembali tombol beli
            delay(600_000)  // 10 menit = 600.000 ms
            isActivated = false // Reset status activated
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        TopBar(
            userName = userName,
            score = 123,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 72.dp, bottom = 80.dp)
        ) {
            item {
                BoosterButton(
                    onBuyClicked = {
                        isActivated = true
                        Toast.makeText(context, "2x Point berhasil dibeli", Toast.LENGTH_SHORT).show()
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
                LevelProgressSection()
            }
        }

        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
        )
    }
}


@Composable
fun TopBar(userName: String, score: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color(0xFFA3D8F4), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Welcome, $userName",
            fontSize = 20.sp,
            color = Color(0xFF0B3D5B),
            fontWeight = FontWeight.SemiBold
        )

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.star_icon),
                contentDescription = "Score Icon",
                modifier = Modifier.size(22.dp),
                tint = Color(0xFFFFC107)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$score",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0B3D5B)
            )
        }
    }
}

@Composable
fun BoosterButton(
    onBuyClicked: () -> Unit
) {
    var isActivated by remember { mutableStateOf(false) }
    var showActivated by remember { mutableStateOf(false) }

    // LaunchedEffect untuk mengatur delay 10 menit
    LaunchedEffect(key1 = isActivated) {
        if (isActivated) {
            // Menunggu selama 10 menit (600.000 ms)
            delay(600_000)
            isActivated = false
            showActivated = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFF68D39C), shape = RoundedCornerShape(100))
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "2x",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Kolom untuk teks "Point" dan deskripsi
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Point",
                    fontSize = 18.sp,
                    color = Color(0xFF0B3D5B),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Double your points for a limited time!",
                    fontSize = 14.sp,
                    color = Color(0xFF0B3D5B),
                    modifier = Modifier.align(Alignment.Start)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Tampilkan "Activated" jika sudah dibeli
        AnimatedVisibility(
            visible = showActivated,
            enter = fadeIn(tween(durationMillis = 500)),
            exit = fadeOut(tween(durationMillis = 500))
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF68D39C))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Activated",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Activated",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Jika belum diaktifkan, tampilkan tombol beli
        if (!isActivated) {
            Button(
                onClick = {

                    onBuyClicked()
                    isActivated = true
                    showActivated = true
                },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF68D39C),
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Beli",
                    fontSize = 14.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun LevelProgressSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        LevelSection(title = "Huruf", count = 5)

        SectionDivider()

        LevelSection(title = "Kata", count = 5)

        SectionDivider()

        LevelSection(title = "Kalimat", count = 5)
    }
}

@Composable
fun SectionDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "★",
            modifier = Modifier.padding(horizontal = 12.dp),
            fontSize = 16.sp,
            color = Color.Gray
        )
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun LevelSection(title: String, count: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Menambahkan padding kiri dan kanan
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF0B3D5B),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(count) { i ->
                LevelCircle(level = i + 1)
            }
        }
    }
}

@Composable
fun LevelCircle(level: Int) {
    Card(
        shape = RoundedCornerShape(50),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF68D39C)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .size(80.dp)
            .clickable {
                // Handle click
            }
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = "L$level",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}






