package com.register.currencyexrate.domain.entities

data class CurrencyInfo(
    val id: String,
    val numCode: String,
    val charCode: String,
    val nominal: Int,
    val name: String,
    val value: Double,
    val previous: Double
)