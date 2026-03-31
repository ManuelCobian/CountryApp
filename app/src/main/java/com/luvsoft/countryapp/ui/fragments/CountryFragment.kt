package com.luvsoft.countryapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.luvsoft.base.ui.activities.BaseActivity
import com.luvsoft.base.ui.fragments.BaseFragment
import com.luvsoft.core.ApiResponseStatus
import com.luvsoft.core.Constants.COUNTRY_SELECTED
import com.luvsoft.core.ui.Country
import com.luvsoft.countryapp.databinding.FragmentCountryBinding
import com.luvsoft.countryapp.ui.adapters.CountryAdapter
import com.luvsoft.countryapp.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import kotlin.getValue
import com.luvsoft.countryapp.R

@AndroidEntryPoint
class CountryFragment : BaseFragment(), CountryAdapter.CallbackClick {

    private var _binding: FragmentCountryBinding? = null

    private val binding get() = _binding!!

    private val viewModel: CountryViewModel by viewModels()

    @Inject
    lateinit var adapters: CountryAdapter

    @Inject
    lateinit var laM: RecyclerView.LayoutManager

    private lateinit var inputManager: InputMethodManager

    private var timer = Timer()

    private var isClear = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        setupRecyclerView()
        initListener()
        setUpViewModel()
        initObservers()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = laM
            adapter = this@CountryFragment.adapters
        }
    }

    private fun setUpViewModel() {
        (activity as? BaseActivity)?.subscribeViewModelToFragment(viewModel, binding.root)
        viewModel.getCountries()
    }

    private fun initListener(){
        with(binding){
            binding.imageClear.setOnClickListener {
                binding.editTextSearch.text?.clear()
                loadCountries()
            }
            editTextSearch.doAfterTextChanged {
                handleSearchChange(it)
                binding.imageClear.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            swipeRefreshLayout.setOnRefreshListener {
                loadCountries()
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
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is ApiResponseStatus.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                is ApiResponseStatus.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
        viewModel.onLoading().observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.onError().observe(viewLifecycleOwner) {
            if (it) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.favoritos_success),
                    Snackbar.LENGTH_LONG
                ).show()
            } else Snackbar.make(
                binding.root,
                getString(R.string.error_favorites),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun handleSearchChange(text: Editable?) {
        isClear = true
        timer.cancel()
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                text?.length?.let {
                    if(it >= 2) {
                        searchCountries(text.toString())
                    }
                }

            }
        }, 100L)
    }

    private fun setUi(country : List<Country>) {
        adapters.submitList(country)
        showEmptyMessage(country.isEmpty())
    }

    private fun searchCountries(toSearch: String){
        lifecycleScope.launch {
            viewModel.getCountriesByName(toSearch)
        }
    }

    private fun loadCountries() {
        lifecycleScope.launch {
            if (evaluateConnection()) viewModel.getCountries()
        }
    }

    private fun showLoading(show: Boolean) {
        binding.layoutLoading.visibility = if (show) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }

    private fun showEmptyMessage(show: Boolean) {
        binding.textViewCompleteMessage.visibility = if (show) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun startDetailCountry(name: String) {
        val bundle = Bundle().apply {
            putString(COUNTRY_SELECTED, name)
        }

        val selectedFragment = CountrySelectedFragment().apply {
            arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, selectedFragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(country: Country) {
        startDetailCountry(country.name.common.toString())
    }

    override fun onFavoriteClicked(country: Country) {
        lifecycleScope.launch {
            viewModel.saveFavorite(country)
        }
    }
}
