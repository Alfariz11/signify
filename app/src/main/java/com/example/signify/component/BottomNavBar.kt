package com.example.signify.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signify.R

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
)

@Composable
fun BottomNavBar(    navController: NavController,
                     modifier: Modifier = Modifier) {
    val navItems = listOf(
        BottomNavItem("home", R.drawable.home_icon, "Home"),
        BottomNavItem("leaderboard", R.drawable.lead_icon, "Ranking"),
        BottomNavItem("kamus", R.drawable.kamus_icon, "Kamus"),
        BottomNavItem("rapidgame", R.drawable.rapid_icon, "Rapid Game"),
        BottomNavItem("profile", R.drawable.profile_icon, "Profil")
    )

    val currentRoute = navController.currentBackStackEntry?.destination?.route

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .navigationBarsPadding()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly, // Spacing antar ikon konsisten
        verticalAlignment = Alignment.CenterVertically
    ) {
        navItems.forEach { item ->
            val selected = currentRoute == item.route

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        if (!selected) {
                            navController.navigate(item.route) {
                                popUpTo("home") { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    }
            ) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.label,
                    modifier = Modifier.size(40.dp), // Ukuran ikon diperbesar
                    tint = Color.Unspecified // Menyesuaikan warna ikon
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.label,
                    fontSize = 12.sp,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    color = Color.Black
                )
            }
        }
    }
}



