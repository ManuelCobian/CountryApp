package com.luvsoft.countryapp.ui.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.luvsoft.core.ui.Country
import javax.inject.Inject

class CountryDiff @Inject constructor() : DiffUtil.ItemCallback<Country>() {
    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.name.common == newItem.name.common
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean = oldItem == newItem

}