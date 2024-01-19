package com.register.currencyexrate.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.ListAdapter
import com.register.currencyexrate.databinding.CurrencyItemBinding
import com.register.currencyexrate.domain.entities.CurrencyInfo

class CurrencyListAdapter: ListAdapter<CurrencyInfo, CurrencyItemViewHolder>(CurrencyItemDiffCallback) {

    private var clickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
        val binding = CurrencyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CurrencyItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
        val currency = getItem(position)
        with(holder.binding) {
            currencyName.text = currency.charCode
            currencyValue.text = currency.value.toString()
        }
        holder.itemView.setOnClickListener {
            clickListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}