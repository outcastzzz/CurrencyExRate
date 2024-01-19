package com.register.currencyexrate.domain.repository

import androidx.lifecycle.LiveData
import com.register.currencyexrate.domain.entities.CurrencyInfo
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun getCurrencyInfo(): List<CurrencyInfo>

    suspend fun getCurrencyInfoWithDate(
        year: String,
        month: String,
        day: String
    ): List<CurrencyInfo>

    suspend fun clearCache()

}