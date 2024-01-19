package com.register.currencyexrate.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.register.currencyexrate.domain.entities.CurrencyInfo

object CurrencyItemDiffCallback: DiffUtil.ItemCallback<CurrencyInfo>() {

    override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem == newItem
    }
}