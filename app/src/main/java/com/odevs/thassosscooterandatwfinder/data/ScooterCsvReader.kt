package com.odevs.thassosscooterandatwfinder.data

import android.content.Context
import android.util.Log
import com.odevs.thassosscooterandatwfinder.model.ScooterItem
import com.opencsv.CSVReaderBuilder
import java.io.InputStreamReader

class ScooterCsvReader(private val context: Context) {
    fun readScooters(): List<ScooterItem> {
        val result = mutableListOf<ScooterItem>()
        val inputStream = context.assets.open("adatbazis.csv")
        val reader = CSVReaderBuilder(InputStreamReader(inputStream, Charsets.UTF_8)) // biztos UTF-8
            .withSkipLines(1)
            .build()

        reader.forEachIndexed { index, tokens ->
            if (tokens.size >= 13) {
                val rawPrice = tokens[6].trim()
                val price = rawPrice
                    .replace("€", "")
                    .replace("EUR", "")
                    .replace(",", ".")
                    .replace("\"", "")
                    .trim()
                    .toDoubleOrNull() ?: run {
                    Log.w("ScooterCsvReader", "⚠️ Hibás ár mező (sor $index): '$rawPrice' — alapértelmezettként 0.0")
                    0.0
                }

                val cm3Clean = tokens[5].trim().replace("[^\\d]".toRegex(), "") // csak szám maradjon
                val cm3 = if (cm3Clean.isNotBlank()) cm3Clean else "0"

                result.add(
                    ScooterItem(
                        id = index,
                        rental = tokens[0].trim(),
                        vehicleType = tokens[1].trim(),
                        category = tokens[2].trim(),
                        brand = tokens[3].trim(),
                        model = tokens[4].trim(),
                        name = "${tokens[3].trim()} ${tokens[4].trim()}",
                        cm3 = cm3,
                        pricePerDay = price,
                        gearType = tokens[7].trim(),
                        availability = tokens[8].trim(),
                        description = tokens[9].trim(),
                        imageUrl = tokens[10].trim(),
                        location = tokens[11].trim(),
                        offerLink = tokens[12].trim()
                    )
                )
            } else {
                Log.w("ScooterCsvReader", "❌ Sor kihagyva (sor $index), túl kevés mező (${tokens.size}): ${tokens.joinToString()}")
            }
        }

        return result
    }
}
