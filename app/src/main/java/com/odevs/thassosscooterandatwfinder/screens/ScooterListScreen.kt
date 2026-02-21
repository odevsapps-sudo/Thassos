package com.odevs.thassosscooterandatwfinder.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.odevs.thassosscooterandatwfinder.data.ScooterCsvReader
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

@Composable
fun ScooterListScreen() {
    val context = LocalContext.current
    val scooters = remember { ScooterCsvReader(context).readScooters() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(scooters) { scooter ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    scooter.imageUrl.takeIf { it.isNotBlank() }?.let { url ->
                        Image(
                            painter = rememberAsyncImagePainter(url),
                            contentDescription = scooter.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Text(scooter.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Márka: ${scooter.brand} ${scooter.model} | ${scooter.cm3} cm³ | ${scooter.gearType}")
                    Text("Bérlés: ${scooter.rental} • Ár: ${scooter.pricePerDay} €/nap")
                    Text("Elérhetőség: ${scooter.availability}")
                    Text("Leírás: ${scooter.description}", maxLines = 3)
                }
            }
        }
    }
}
