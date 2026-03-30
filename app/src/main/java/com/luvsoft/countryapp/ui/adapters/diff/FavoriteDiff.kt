package com.luvsoft.countryapp.ui.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity
import javax.inject.Inject

class FavoriteDiff @Inject constructor() : DiffUtil.ItemCallback<CountryFavoriteEntity>() {


    override fun areItemsTheSame(
        oldItem: CountryFavoriteEntity,
        newItem: CountryFavoriteEntity
    ): Boolean = oldItem.cca3 == newItem.cca3

    override fun areContentsTheSame(
        oldItem: CountryFavoriteEntity,
        newItem: CountryFavoriteEntity
    ): Boolean = oldItem == newItem
}