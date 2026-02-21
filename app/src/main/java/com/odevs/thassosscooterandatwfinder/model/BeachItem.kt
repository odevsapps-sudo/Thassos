package com.odevs.thassosscooterandatwfinder.model

data class BeachItem(
    val name: String,
    val city: String,
    val description: String,
    val parking: String,
    val googleMapsUrl: String,
    val imageUrl: String? = null
)
