package com.odevs.thassosscooterandatwfinder.data.remote.email

import com.odevs.thassosscooterandatwfinder.model.EmailRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailService {
    @POST("api/v1.0/email/send")
    fun sendEmail(@Body request: EmailRequest): Call<Void>
}
