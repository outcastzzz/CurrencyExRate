package com.register.currencyexrate.domain.useCases

import com.register.currencyexrate.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencyWithDateUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend fun getCurrencyInfoWithDate(
        year: String,
        month: String,
        day: String
    ) = repository.getCurrencyInfoWithDate(year, month, day)

}