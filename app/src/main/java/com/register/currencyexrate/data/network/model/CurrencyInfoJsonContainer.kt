package com.register.currencyexrate.data.network.model

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyInfoJsonContainer(
    @SerializedName("Valute")
    @Expose
    val json: JsonObject? = null
)