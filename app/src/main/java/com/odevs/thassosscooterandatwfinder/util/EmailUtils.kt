package com.odevs.thassosscooterandatwfinder.util

import android.util.Log
import android.widget.Toast
import com.odevs.thassosscooterandatwfinder.data.remote.email.RetrofitInstance
import com.odevs.thassosscooterandatwfinder.model.EmailRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun sendEmail(
    vehicleType: String,
    name: String,
    cm3: String,
    pricePerDay: String,
    email: String,
    context: android.content.Context
) {
    val request = EmailRequest(
        service_id = "service_oo...",
        template_id = "template_...",
        user_id = "YOUR_PUBLIC_KEY",
        template_params = mapOf(
            "vehicleType" to vehicleType,
            "name" to name,
            "cm3" to cm3,
            "pricePerDay" to pricePerDay,
            "email" to email
        )
    )

    RetrofitInstance.api.sendEmail(request).enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                Toast.makeText(context, "Ajánlatkérés elküldve!", Toast.LENGTH_SHORT).show()
                Log.d("EMAIL", "Email sent successfully")
            } else {
                Toast.makeText(context, "Hiba az email küldésekor", Toast.LENGTH_SHORT).show()
                Log.e("EMAIL", "Failed: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            Toast.makeText(context, "Hálózati hiba: ${t.message}", Toast.LENGTH_SHORT).show()
            Log.e("EMAIL", "Network error: ${t.message}")
        }
    })
}
