package com.register.currencyexrate.di

import android.app.Application
import com.register.currencyexrate.data.database.AppDatabase
import com.register.currencyexrate.data.database.CurrencyInfoDao
import com.register.currencyexrate.data.network.ApiFactory
import com.register.currencyexrate.data.network.ApiService
import com.register.currencyexrate.data.repository.CurrencyRepositoryImpl
import com.register.currencyexrate.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepository(impl: CurrencyRepositoryImpl): CurrencyRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideCurrencyDao(
            application: Application
        ): CurrencyInfoDao {
            return AppDatabase.getInstance(application).currencyInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

    }

}