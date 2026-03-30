package com.luvsoft.countryapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.luvsoft.base.ui.activities.BaseActivity
import com.luvsoft.base.ui.fragments.BaseFragment
import com.luvsoft.core.ApiResponseStatus
import com.luvsoft.core.Constants.COUNTRY_SELECTED
import com.luvsoft.core.ui.Country
import com.luvsoft.countryapp.R
import com.luvsoft.countryapp.databinding.FragmentCountrySelectedBinding
import com.luvsoft.countryapp.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class CountrySelectedFragment : BaseFragment() {

    private var _binding: FragmentCountrySelectedBinding? = null

    private val binding get() = _binding!!

    private val viewModel: CountryViewModel by viewModels()

    var country = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountrySelectedBinding.inflate(inflater, container, false)
        val view = binding.root
        country = arguments?.getString(COUNTRY_SELECTED) ?: ""
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initListener()
        setUpViewModel()
        initObservers()
    }


    private fun setUpViewModel() {
        (activity as? BaseActivity)?.subscribeViewModelToFragment(viewModel, binding.root)
        viewModel.getCountriesByName(country)
    }

    private fun initListener(){
        with(binding){
            imageViewClose.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, CountryFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun initObservers() {

        viewModel.onCountriesFoundChange().observe(viewLifecycleOwner) {
            setUi(it)
        }
        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponseStatus.Loading -> {

                }
                is ApiResponseStatus.Error -> {
                }

                is ApiResponseStatus.Success -> {

                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun setUi(country: List<Country>) {
        val item = country.firstOrNull() ?: return

        with(binding) {

            tvCommonName.text = item.name.common ?: getString(R.string.no_name)

            tvOfficialName.text = item.name.official ?: getString(R.string.no_official_name)

            tvCapital.text = item.capital.joinToString(", ")
                .ifEmpty { getString(R.string.no_capital) }

            tvRegion.text = item.region ?: getString(R.string.no_region)

            tvSubregion.text = item.subregion ?: getString(R.string.no_subregion)

            tvPopulation.text = item.population
                ?.let { String.format("%,d", it) }
                ?: getString(R.string.no_data)

            tvArea.text = item.area
                ?.let { String.format("%,.0f", it) }
                ?: getString(R.string.no_data)

            tvCarSide.text = item.car?.side?.uppercase()
                ?: getString(R.string.no_data)

            tvLanguages.text = item.languages.values
                .joinToString("\n")
                .ifEmpty { getString(R.string.no_languages) }

            tvTimezones.text = item.timezones
                .joinToString(", ")
                .ifEmpty { getString(R.string.no_timezones) }

            tvCurrencies.text = item.currencies.map { (code, currency) ->
                "$code (${currency.symbol ?: ""} ${currency.name ?: ""})"
            }.joinToString("\n")
                .ifEmpty { getString(R.string.no_currencies) }

            Glide.with(this@CountrySelectedFragment)
                .load(item.flags?.png)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgFlag)

            Glide.with(this@CountrySelectedFragment)
                .load(item.coatOfArms?.png)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(imgCoatOfArms)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}