package com.odevs.thassosscooterandatwfinder.data.logging

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

object ClickLogger {
    private const val apiUrl = "https://v1.nocodeapi.com/odevs/google_sheets/GnmTRJCoCTvjWBMi?tabId=Clicks"

    fun logOfferClick() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // A sor: [timestamp, "offer_click"]
                val row = JSONArray().apply {
                    put(System.currentTimeMillis())
                    put("offer_click")
                }

                val jsonBody = JSONObject().apply {
                    put("data", JSONArray().put(row))
                }

                val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaTypeOrNull())

                val request = Request.Builder()
                    .url(apiUrl)
                    .post(requestBody)
                    .build()

                OkHttpClient().newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("ClickLogger", "Failed to log offer click", e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (!response.isSuccessful) {
                            Log.e("ClickLogger", "Logging failed: ${response.code}")
                        } else {
                            Log.d("ClickLogger", "Click logged successfully")
                        }
                    }
                })
            } catch (e: Exception) {
                Log.e("ClickLogger", "Exception during logging", e)
            }
        }
    }
}
