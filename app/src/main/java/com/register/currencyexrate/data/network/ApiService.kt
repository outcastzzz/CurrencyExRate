package com.register.currencyexrate.data.network

import com.register.currencyexrate.data.network.model.CurrencyInfoJsonContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("daily_json.js")
    suspend fun getCurrencyData(): Response<CurrencyInfoJsonContainer>

    @GET("archive/{year}/{month}/{day}/daily_json.js")
    suspend fun getCurrencyWithDate(
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("day") day: String
    ): Response<CurrencyInfoJsonContainer>

}