package com.luvsoft.countryapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luvsoft.countryapp.R
import com.luvsoft.countryapp.databinding.ItemCountryBinding
import com.luvsoft.countryapp.ui.adapters.diff.FavoriteDiff
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity
import javax.inject.Inject

class FavoriteAdapter @Inject constructor(
    private val diff: FavoriteDiff,
    private val callbackClick: CallbackClick
) : ListAdapter<CountryFavoriteEntity, FavoriteAdapter.ViewHolder>(diff) {

    inner class ViewHolder(
        val binding: ItemCountryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    callbackClick.onItemClicked(getItem(position))
                }
            }

            binding.imgDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    callbackClick.onItemDeleteClicked(item)
                }
            }
        }
    }

    interface CallbackClick {
        fun onItemClicked(country: CountryFavoriteEntity?)
        fun onItemDeleteClicked(country: CountryFavoriteEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCountryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = getItem(position)

        with(holder.binding) {
            tvName.text = country.commonName
            tvOfficial.text = country.officialName
            tvCapital.text = country.capital
            imgFavorite.visibility = View.GONE
            imgDelete.visibility = View.VISIBLE

            Glide.with(root.context)
                .load(country.flagUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(salesImageView)
        }
    }
}