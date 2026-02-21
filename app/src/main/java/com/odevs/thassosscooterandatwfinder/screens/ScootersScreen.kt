package com.odevs.thassosscooterandatwfinder.screens


import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.odevs.thassosscooterandatwfinder.R
import com.odevs.thassosscooterandatwfinder.data.ScooterCsvReader
import com.odevs.thassosscooterandatwfinder.model.ScooterItem
import android.content.Context
import androidx.compose.foundation.clickable
import com.odevs.thassosscooterandatwfinder.MainActivity
import com.odevs.thassosscooterandatwfinder.util.LanguageManager
import com.odevs.thassosscooterandatwfinder.localization.LocalStrings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScooterScreen(navController: NavController) {
    val context = LocalContext.current
    val strings = LocalStrings.current
    val allScooters = remember { ScooterCsvReader(context).readScooters() }
    var selectedScooter by remember { mutableStateOf<ScooterItem?>(null) }
    var selectedLocation by remember { mutableStateOf("") }
    var selectedCm3 by remember { mutableStateOf("") }
    var selectedVehicleType by remember { mutableStateOf("") }
    var selectedBrand by remember { mutableStateOf("") }
    var filteredList by remember { mutableStateOf(allScooters) }

    val locations = remember { allScooters.map { it.location }.distinct().sorted() }
    val cm3s = remember {
        allScooters
            .mapNotNull { it.cm3.toIntOrNull()?.let { num -> num to it.cm3 } }
            .distinctBy { it.first }
            .sortedBy { it.first }
            .map { it.second }
    }
    val vehicleTypes = remember { allScooters.map { it.vehicleType }.distinct().sorted() }
    val brands = remember { allScooters.map { it.brand }.distinct().sorted() }

    fun applyFilter() {
        filteredList = allScooters.filter {
            (selectedLocation.isEmpty() || it.location == selectedLocation) &&
                    (selectedVehicleType.isEmpty() || it.vehicleType == selectedVehicleType) &&
                    (selectedBrand.isEmpty() || it.brand == selectedBrand)
        }
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
                        Text(strings.vehicles)
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
            modifier = Modifier.fillMaxSize()
        ) {
            // 💡 HÁTTÉRKÉP – padding nélkül, teljes képernyőre
            AsyncImage(
                model = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 💡 FEKETE OVERLAY – szintén padding nélkül
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000))
            )

            // 💡 A teljes tartalom paddinggel
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(start = 2.dp, end = 4.dp, bottom = 4.dp, top = 0.dp)
            )  {
                DropdownSelector(
                    label = strings.vehicle_type,
                    options = vehicleTypes,
                    selectedOption = selectedVehicleType,
                    onOptionSelected = { selectedVehicleType = it }
                )


                DropdownSelector(
                    label = strings.location,
                    options = locations,
                    selectedOption = selectedLocation,
                    onOptionSelected = { selectedLocation = it }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { applyFilter() },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E86DE),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = strings.filter_button_text,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            selectedLocation = ""
                            selectedVehicleType = ""
                            selectedBrand = ""
                            filteredList = allScooters
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = strings.clear_button_text,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                if (filteredList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = strings.no_results_found,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredList) { scooter ->
                            ScooterItemCard(
                                scooter = scooter,
                                onDetailsClick = { navController.navigate("quote_form/${scooter.id}") },
                                onOfferClick = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        try {
                                            val client = OkHttpClient()

                                            val json = JSONObject()
                                            json.put("timestamp", System.currentTimeMillis())
                                            json.put("scooter_name", scooter.name)
                                            json.put("itemId", scooter.id)

                                            val requestBody = json.toString()
                                                .toRequestBody("application/json".toMediaTypeOrNull())

                                            val request = Request.Builder()
                                                .url("https://hook.eu2.make.com/p4kgye8fpkf3fkssfpsd85rpohoties9")
                                                .post(requestBody)
                                                .build()

                                            client.newCall(request).execute()
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }

                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(scooter.offerLink))
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = Color.Black) },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black,
                focusedContainerColor = Color.White,      // 👈 HÁTTÉR SZÍN
                unfocusedContainerColor = Color.White     // 👈 HÁTTÉR SZÍN
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun ScooterItemCard(
    scooter: ScooterItem,
    onDetailsClick: () -> Unit,
    onOfferClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onDetailsClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (scooter.imageUrl.isNotBlank()) {
                AsyncImage(
                    model = scooter.imageUrl,
                    contentDescription = scooter.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = scooter.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text("Type: ${scooter.vehicleType} | ${scooter.cm3} cm³ | ${scooter.gearType}")
            Text("Rental: ${scooter.rental}")
            Text("Location: ${scooter.location}")

            Spacer(modifier = Modifier.height(12.dp))
            val strings = LocalStrings.current
            Button(
                onClick = onOfferClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E86DE),
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(strings.request_offer_button)
            }
        }
    }
}


