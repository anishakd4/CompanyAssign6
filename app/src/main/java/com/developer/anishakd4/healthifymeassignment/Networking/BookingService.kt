package com.developer.anishakd4.healthifymeassignment.Networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BookingService {

    private val BASE_URL = "https://www.x123healthifyme.com";

    fun getBookingInfoService(): GetDataInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetDataInterface::class.java)
    }

}