package com.register.currencyexrate.domain.useCases

import com.register.currencyexrate.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrencyInfoUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend fun getCurrency() = repository.getCurrencyInfo()

}