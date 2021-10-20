package com.developer.anishakd4.healthifymeassignment.Networking

import com.developer.anishakd4.healthifymeassignment.Model.BookingInfo
import retrofit2.Response
import retrofit2.http.GET

interface GetDataInterface {

    @GET("api/v2/booking/slots/all/?expert_username=sakshi.sharma@healthifyme.com&username=hme-testmnn1937@example.com&api_key=ea2b9e93ae899eb8f63f6dcc5995ef6409bf15f3")
    suspend fun getCountires(): Response<List<BookingInfo>>
}