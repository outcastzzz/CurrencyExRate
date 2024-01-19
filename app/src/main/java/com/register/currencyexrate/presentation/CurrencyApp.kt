package com.register.currencyexrate.presentation

import android.app.Application
import androidx.work.Configuration
import com.register.currencyexrate.di.DaggerApplicationComponent
import javax.inject.Inject

class CurrencyApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

}