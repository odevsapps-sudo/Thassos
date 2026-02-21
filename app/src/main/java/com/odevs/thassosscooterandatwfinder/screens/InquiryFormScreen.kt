package com.odevs.thassosscooterandatwfinder.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.odevs.thassosscooterandatwfinder.model.ScooterItem

@Composable
fun InquiryFormScreen(scooter: ScooterItem) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Ajánlatkérés", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        Text("Jármű: ${scooter.name}")
        Text("Típus: ${scooter.brand} ${scooter.model}, ${scooter.cm3} cm³, ${scooter.gearType}")
        Text("Bérlő: ${scooter.rental}")
        Text("Ár: ${scooter.pricePerDay} €/nap")
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail címed") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val subject = "Ajánlatkérés – ${scooter.name}"
                val body = "Jármű adatok:\n" +
                        "Név: ${scooter.name}\n" +
                        "Típus: ${scooter.brand} ${scooter.model}\n" +
                        "cm³: ${scooter.cm3}\n" +
                        "Sebességváltó: ${scooter.gearType}\n" +
                        "Bérlő: ${scooter.rental}\n" +
                        "Ár: ${scooter.pricePerDay} €/nap\n" +
                        "Elérhetőség: ${scooter.availability}\n" +
                        "Leírás: ${scooter.description}\n\n" +
                        "Felhasználó e-mail címe: $email\n\n" +
                        "Ez az igénylés a Thassos Scooter & ATV Finder appból érkezett."

                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("thecat80@gmail.com"))
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, body)
                }
                context.startActivity(intent)
                showConfirmation = true
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E86DE))
        ) {
            Text("Ajánlatkérés küldése")
        }

        if (showConfirmation) {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Köszönjük az érdeklődést, a kereskedő hamarosan jelentkezni fog.", color = Color(0xFF2E7D32))
        }
    }
}
