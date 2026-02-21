package com.odevs.thassosscooterandatwfinder.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.odevs.thassosscooterandatwfinder.R
import com.odevs.thassosscooterandatwfinder.localization.LocalStrings
import com.odevs.thassosscooterandatwfinder.model.BeachItem
import com.odevs.thassosscooterandatwfinder.model.TripItem
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeachesScreen(beaches: List<BeachItem>, trips: List<TripItem>) {
    val context = LocalContext.current
    val strings = LocalStrings.current

    var selectedCity by remember { mutableStateOf("") }
    var selectedParking by remember { mutableStateOf("") }
    var selectedDuration by remember { mutableStateOf("") }

    val allCities = beaches.map { it.city }.distinct().sorted()
    val allParkings = beaches.map { it.parking }.distinct().sorted()
    val allDurations = trips.map { it.duration }.distinct().sorted()

    val filteredBeaches = beaches.filter {
        (selectedCity.isBlank() || it.city == selectedCity) &&
                (selectedParking.isBlank() || it.parking == selectedParking)
    }

    val filteredTrips = trips.filter {
        (selectedDuration.isBlank() || it.duration == selectedDuration)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_round),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(strings.beaches)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF121212),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // ✅ csak ez
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                FilterDropdown(strings.beach_city, allCities, selectedCity) { selectedCity = it }
                Spacer(modifier = Modifier.height(8.dp))
                FilterDropdown(strings.beach_parking, allParkings, selectedParking) { selectedParking = it }
                Spacer(modifier = Modifier.height(8.dp))
                FilterDropdown(strings.trip_duration, allDurations, selectedDuration) { selectedDuration = it }
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {


                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E86DE),
                            contentColor = Color.White
                        )
                    ) {
                        Text(strings.filter_button_text)
                    }

                    Button(
                        onClick = {
                            selectedCity = ""
                            selectedParking = ""
                            selectedDuration = ""
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(strings.clear_button_text)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (filteredBeaches.isEmpty() && filteredTrips.isEmpty()) {
                    Text(
                        text = strings.no_results_found,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(filteredBeaches) { beach ->
                            BeachCard(beach = beach, context = context, label = strings.open_in_maps, parkingLabel = strings.beach_parking)
                        }
                        if (filteredTrips.isNotEmpty()) {
                            item {
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(strings.trip_label, style = MaterialTheme.typography.headlineSmall, color = Color.White)
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                            items(filteredTrips) { trip ->
                                TripCard(trip = trip, context = context, label = strings.open_in_maps, durationLabel = strings.trip_duration)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(label: String, options: List<String>, selected: String, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun BeachCard(beach: BeachItem, context: android.content.Context, label: String, parkingLabel: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(beach.name, style = MaterialTheme.typography.titleLarge)
            Text(beach.city, style = MaterialTheme.typography.labelMedium)

            Spacer(modifier = Modifier.height(8.dp))

            beach.imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(beach.description)
            Text("$parkingLabel: ${beach.parking}", style = MaterialTheme.typography.labelSmall)

            Spacer(modifier = Modifier.height(12.dp))

            var isPressed by remember { mutableStateOf(false) }
            val animatedColor by animateColorAsState(
                targetValue = if (isPressed) Color(0xFF00BFFF) else Color(0xFF87CEEB),
                label = "buttonColor"
            )

            Button(
                onClick = {
                    isPressed = true
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(beach.googleMapsUrl))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    ContextCompat.startActivity(context, intent, null)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = animatedColor,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(label)
            }
        }
    }
}

@Composable
fun TripCard(trip: TripItem, context: android.content.Context, label: String, durationLabel: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
    ) {
        val strings = LocalStrings.current
        Column(modifier = Modifier.padding(16.dp)) {
            Text(trip.name, style = MaterialTheme.typography.titleLarge)
            Text("${strings.route_label}: ${trip.route}", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text("${strings.sights_label}: ${trip.sights}", style = MaterialTheme.typography.bodyMedium)
            Text("$durationLabel: ${trip.duration}", style = MaterialTheme.typography.labelSmall)

            Spacer(modifier = Modifier.height(12.dp))

            var isPressed by remember { mutableStateOf(false) }
            val animatedColor by animateColorAsState(
                targetValue = if (isPressed) Color(0xFF00BFFF) else Color(0xFF87CEEB),
                label = "buttonColor"
            )

            Button(
                onClick = {
                    isPressed = true
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trip.googleMapsUrl))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    ContextCompat.startActivity(context, intent, null)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = animatedColor,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(label)
            }
        }
    }
}
