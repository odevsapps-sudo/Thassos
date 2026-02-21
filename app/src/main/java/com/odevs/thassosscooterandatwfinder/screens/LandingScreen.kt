package com.odevs.thassosscooterandatwfinder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LandingScreen(onCardClick: (String) -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF1F5))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White.copy(alpha = 0.9f), shape = RoundedCornerShape(32.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ride Finder",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                HomeButton("Scooters", Icons.Default.TwoWheeler) {
                    onCardClick("scooters")
                }
                HomeButton("ATWs", Icons.Default.DirectionsCar) {
                    onCardClick("atws")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                HomeButton("Restaurants", Icons.Default.Restaurant) {
                    onCardClick("contact")
                }
                HomeButton("Beaches", Icons.Default.BeachAccess) {
                    onCardClick("settings")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO: promotions */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp)
            ) {
                Text(text = "+ Promotions")
            }
        }
    }
}

@Composable
fun HomeButton(label: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(120.dp)
            .background(Color.White, RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(48.dp))
        Text(text = label)
    }
}
