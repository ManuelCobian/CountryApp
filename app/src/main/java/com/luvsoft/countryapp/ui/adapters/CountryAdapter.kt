package com.luvsoft.countryapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luvsoft.core.ui.Country
import com.luvsoft.countryapp.R
import com.luvsoft.countryapp.databinding.ItemCountryBinding
import com.luvsoft.countryapp.ui.adapters.diff.CountryDiff
import javax.inject.Inject

class CountryAdapter @Inject constructor(
    private val diff: CountryDiff,
    private val callbackClick: CallbackClick
) : ListAdapter<Country, CountryAdapter.ViewHolder>(diff) {

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

            binding.imgFavorite.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    callbackClick.onFavoriteClicked(item)
                }
            }
        }
    }

    interface CallbackClick {
        fun onItemClicked(country: Country)
        fun onFavoriteClicked(country: Country)
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
            tvName.text = country.name.common
            tvOfficial.text = country.name.official
            tvCapital.text = country.capital.joinToString(", ") ?: "Sin capital"

            Glide.with(root.context)
                .load(country.flags?.png)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(salesImageView)
        }
    }
}