package com.odevs.thassosscooterandatwfinder.data.remote.email

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: EmailService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.emailjs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmailService::class.java)
    }
}