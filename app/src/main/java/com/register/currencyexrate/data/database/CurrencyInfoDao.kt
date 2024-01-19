package com.register.currencyexrate.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyInfoDao {

    @Query("SELECT * FROM currency_table")
    fun getCurrencyInfo(): List<CurrencyInfoDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyInfo(currencyList: List<CurrencyInfoDbModel>)
}