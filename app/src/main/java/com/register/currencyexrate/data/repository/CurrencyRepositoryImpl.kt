package com.register.currencyexrate.data.repository

import com.register.currencyexrate.data.database.CurrencyInfoDao
import com.register.currencyexrate.data.mapper.CurrencyMapper
import com.register.currencyexrate.data.network.ApiService
import com.register.currencyexrate.domain.entities.CurrencyInfo
import com.register.currencyexrate.domain.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyInfoDao,
    private val mapper: CurrencyMapper,
    private val apiService: ApiService
): CurrencyRepository {

    override suspend fun getCurrencyInfo(): List<CurrencyInfo> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getCurrencyData()
            when (response.code()) {
                404 -> emptyList()
                else -> {
                    val list = mapper.mapJsonContainerToListCurrencyInfo(response.body()!!)
                    val dbModelList = list.map(mapper::mapDtoToDbModel)
                    currencyDao.insertCurrencyInfo(dbModelList)
                    val entityList = currencyDao.getCurrencyInfo()
                    val result = entityList.map(mapper::mapDbModelToEntity)
                    result
                }
            }
        }
    }

    override suspend fun getCurrencyInfoWithDate(
        year: String,
        month: String,
        day: String
    ): List<CurrencyInfo> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getCurrencyWithDate(year, month, day)
            when (response.code()) {
                404 -> emptyList()
                else -> {
                    val list = mapper.mapJsonContainerToListCurrencyInfo(response.body()!!)
                    val dbModelList = list.map(mapper::mapDtoToDbModel)
                    currencyDao.insertCurrencyInfo(dbModelList)
                    val entityList = currencyDao.getCurrencyInfo()
                    val result = entityList.map(mapper::mapDbModelToEntity)
                    result
                }
            }
        }
    }

    override suspend fun clearCache() {
        currencyDao.clearCache()
    }
}