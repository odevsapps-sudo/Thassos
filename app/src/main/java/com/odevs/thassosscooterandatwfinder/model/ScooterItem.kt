package com.odevs.thassosscooterandatwfinder.model

data class ScooterItem(
    val id: Int,
    val rental: String,
    val vehicleType: String,
    val category: String,
    val brand: String,            // ✅ új mező
    val model: String,            // ✅ új mező
    val name: String,             // brand + model kombináció
    val cm3: String,
    val pricePerDay: Double,
    val gearType: String,
    val availability: String,
    val description: String,
    val imageUrl: String,
    val location: String,
    val offerLink: String
)
