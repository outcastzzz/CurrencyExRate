package com.register.currencyexrate.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.register.currencyexrate.domain.entities.CurrencyInfo
import com.register.currencyexrate.domain.useCases.ClearCacheUseCase
import com.register.currencyexrate.domain.useCases.GetCurrencyInfoUseCase
import com.register.currencyexrate.domain.useCases.GetCurrencyWithDateUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getCurrencyInfoUseCase: GetCurrencyInfoUseCase,
    private val getCurrencyWithDateUseCase: GetCurrencyWithDateUseCase,
    private val clearCacheUseCase: ClearCacheUseCase
): ViewModel() {

    private val _currentCharCode = MutableLiveData<String>()
    val currentCharCode: LiveData<String> = _currentCharCode

    private val _currentCount = MutableLiveData<Double>()
    val currentCount: LiveData<Double> = _currentCount

    private val _currentName = MutableLiveData<String>()
    val currentName: LiveData<String> = _currentName

    private val _currencyListDate = MutableLiveData<List<CurrencyInfo>>()
    val currencyListDate: LiveData<List<CurrencyInfo>> = _currencyListDate

    private val _errorMessage = MutableLiveData<Boolean>()
    val errorMessage: LiveData<Boolean> = _errorMessage

    fun getCurrencyInfoNow() {
        viewModelScope.launch {
            val response = getCurrencyInfoUseCase.getCurrency()
            _currencyListDate.value = response

            if(response.isEmpty()) {
                _errorMessage.value = true
            } else {
                _errorMessage.value = false
                Log.d("ViewModelTag", "$response")
            }
        }
    }

    fun getCurrencyWithDate(
        year: String,
        month: String,
        day: String,
    ) {
        viewModelScope.launch {
            val currencyInfoLiveData = getCurrencyWithDateUseCase.getCurrencyInfoWithDate(year, month, day)
            _currencyListDate.value = currencyInfoLiveData

            if(currencyInfoLiveData.isEmpty()) {
                _errorMessage.value = true
            } else {
                _errorMessage.value = false
                Log.d("ViewModelTag", "$currencyInfoLiveData")
            }
        }
    }

    fun getClickedItemName(name: String) {
        _currentName.value = name
    }

    fun getClickedItemCharCode(charCode: String) {
        _currentCharCode.value = charCode
    }

    fun getClickedItemCount(count: Double) {
        _currentCount.value = count
    }

    private fun clearCache() {
        viewModelScope.launch {
            clearCacheUseCase.clearCache()
        }
    }

    override fun onCleared() {
        super.onCleared()
        clearCache()
        _currencyListDate.value = emptyList()
        _currentCount.value = 0.0
        _currentName.value = ""
        _currentCharCode.value = ""
        _errorMessage.value = false
    }


}