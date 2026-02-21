package com.odevs.thassosscooterandatwfinder.model

data class RestaurantItem(
    val name: String,
    val address: String,
    val foodType: String,
    val priceCategory: String,
    val googleMapsUrl: String,
    val imageUrl: String
)