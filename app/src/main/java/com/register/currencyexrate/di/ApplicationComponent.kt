package com.register.currencyexrate.di

import android.app.Application
import com.register.currencyexrate.presentation.CurrencyApp
import com.register.currencyexrate.presentation.CurrencyInfoFragment
import com.register.currencyexrate.presentation.ListOfCurrencyFragment
import com.register.currencyexrate.presentation.MainActivity
import com.register.currencyexrate.presentation.SplashFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(fragment: SplashFragment)

    fun inject(fragment: ListOfCurrencyFragment)

    fun inject(fragment: CurrencyInfoFragment)

    fun inject(application: CurrencyApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}