package com.odevs.thassosscooterandatwfinder.data

import com.odevs.thassosscooterandatwfinder.model.TripItem

object FakeTripsProvider {
    val tripList = listOf(
        TripItem(
            name = "Panagia – Potamia – Golden Beach Circuit",
            route = "Panagia – Potamia – Golden Beach",
            sights = "Panagia village, local museum, viewpoints, Golden Beach",
            duration = "2 hours",
            googleMapsUrl = "https://www.google.com/maps/dir/Panagia,+Thassos/Potamia,+Thassos/Golden+Beach,+Thassos"
        ),
        TripItem(
            name = "Aliki Archaeological Tour",
            route = "Limenas – Aliki",
            sights = "Ruins of Aliki, ancient marble quarry, crystal-clear bay",
            duration = "3–4 hours",
            googleMapsUrl = "https://www.google.com/maps/dir/Limenas,+Thassos/Aliki,+Thassos"
        ),
        TripItem(
            name = "Kazaviti and Maries Tour",
            route = "Prinos – Kazaviti – Maries",
            sights = "Traditional villages, forest walk, waterfall in Maries",
            duration = "4 hours",
            googleMapsUrl = "https://www.google.com/maps/dir/Prinos,+Thassos/Kazaviti,+Thassos/Maries,+Thassos"
        ),
        TripItem(
            name = "Giola Natural Pool",
            route = "Potos – Astris – Giola",
            sights = "Rocky coastline, natural pool (Giola)",
            duration = "2–3 hours",
            googleMapsUrl = "https://www.google.com/maps/dir/Potos,+Thassos/Giola,+Thassos"
        )
    )
}
