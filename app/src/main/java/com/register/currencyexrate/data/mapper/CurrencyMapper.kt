package com.register.currencyexrate.data.mapper

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.register.currencyexrate.data.database.CurrencyInfoDao
import com.register.currencyexrate.data.database.CurrencyInfoDbModel
import com.register.currencyexrate.data.network.model.CurrencyInfoDto
import com.register.currencyexrate.data.network.model.CurrencyInfoJsonContainer
import com.register.currencyexrate.domain.entities.CurrencyInfo
import javax.inject.Inject

class CurrencyMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: CurrencyInfoDto) = CurrencyInfoDbModel(
        id = dto.id,
        numCode = dto.numCode,
        charCode = dto.charCode,
        nominal = dto.nominal,
        name = dto.name,
        value = dto.value,
        previous = dto.previous
    )

    fun mapJsonContainerToListCurrencyInfo(jsonContainer: CurrencyInfoJsonContainer): List<CurrencyInfoDto> {
        Log.d("MapperTagCurr", "$jsonContainer")
        val result = mutableListOf<CurrencyInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        Log.d("MapperTagCurr", "$jsonObject")
        val currencyKeySet = jsonObject.keySet()
        Log.d("MapperTagCurr", "$currencyKeySet")
        for (currencyKey in currencyKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(currencyKey)
            Log.d("MapperTagCurr", "$currencyJson")
            try {
                val currencyInfo = Gson().fromJson(currencyJson, CurrencyInfoDto::class.java)
                Log.d("MapperTagCurr", "$currencyInfo")
                result.add(currencyInfo)
            } catch (e: JsonParseException) {
                Log.e("MapperError", "Error parsing currency JSON", e)
            }
            Log.d("MapperTagCurr", " $result")
        }
        Log.d("MapperTagCurr", "$result")
        return result
    }

    fun mapDbModelToEntity(dbModel: CurrencyInfoDbModel) = CurrencyInfo(
        id = dbModel.id,
        numCode = dbModel.numCode,
        charCode = dbModel.charCode,
        nominal = dbModel.nominal,
        name = dbModel.name,
        value = dbModel.value,
        previous = dbModel.previous
    )

}