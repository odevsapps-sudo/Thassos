package com.odevs.thassosscooterandatwfinder.screens

import android.content.Intent
import android.net.Uri
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
import com.odevs.thassosscooterandatwfinder.data.FakeRestaurantsProvider
import com.odevs.thassosscooterandatwfinder.localization.LocalStrings
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantsScreen() {
    val restaurants = FakeRestaurantsProvider.restaurantList
    val context = LocalContext.current
    val strings = LocalStrings.current

    var selectedCity by remember { mutableStateOf("") }
    var selectedPrice by remember { mutableStateOf("") }
    var selectedFoodType by remember { mutableStateOf("") }

    val allCities = restaurants.map { it.address.split(",").firstOrNull()?.trim() ?: "" }.distinct().sorted()
    val allPrices = restaurants.map { it.priceCategory }.distinct().sorted()
    val allFoodTypes = restaurants.map { it.foodType }.distinct().sorted()

    val filteredRestaurants = restaurants.filter {
        (selectedCity.isBlank() || it.address.contains(selectedCity, ignoreCase = true)) &&
                (selectedFoodType.isBlank() || it.foodType.equals(selectedFoodType, ignoreCase = true))
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
                        Text(strings.restaurants)
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
                .padding(innerPadding)

        ) {
            AsyncImage(
                model = "https://www.dietaline.hu/public/cache/recipe/images/6/350x350_57dc0fb39a004.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                RestaurantFilterDropdown(strings.restaurant_city, allCities, selectedCity) { selectedCity = it }
                Spacer(modifier = Modifier.height(8.dp))
                RestaurantFilterDropdown(strings.restaurant_food, allFoodTypes, selectedFoodType) { selectedFoodType = it }
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E86DE))
                    ) {
                        Text(strings.filter_button_text, color = Color.White)
                    }
                    Button(
                        onClick = {
                            selectedCity = ""
                            selectedFoodType = ""
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB0C4DE))
                    ) {
                        Text(strings.clear_button_text, color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (filteredRestaurants.isEmpty()) {
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
                        items(filteredRestaurants) { restaurant ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(restaurant.name, style = MaterialTheme.typography.titleLarge)
                                    Text(restaurant.address, style = MaterialTheme.typography.labelMedium)
                                    Text("${strings.restaurant_food}: ${restaurant.foodType}")
                                    Text("${strings.restaurant_price}: ${restaurant.priceCategory}")

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Button(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.googleMapsUrl))
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                            ContextCompat.startActivity(context, intent, null)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF87CEEB),
                                            contentColor = Color.Black
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Map,
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(strings.open_in_maps)
                                    }
                                }
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
fun RestaurantFilterDropdown(label: String, options: List<String>, selected: String, onSelected: (String) -> Unit) {
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
