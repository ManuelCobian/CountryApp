package com.luvsoft.countryapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.luvsoft.base.ui.activities.BaseActivity
import com.luvsoft.base.ui.fragments.BaseFragment
import com.luvsoft.core.Constants.COUNTRY_SELECTED
import com.luvsoft.countryapp.R
import com.luvsoft.countryapp.databinding.FragmentFavoritesBinding
import com.luvsoft.countryapp.ui.adapters.FavoriteAdapter
import com.luvsoft.countryapp.viewmodel.FavoritesViewModel
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class FavoritesFragment : BaseFragment(), FavoriteAdapter.CallbackClick {

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    @Inject
    lateinit var adapters: FavoriteAdapter

    @Inject
    lateinit var laM: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        initListener()
        setUpViewModel()
        initObservers()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = laM
            adapter = this@FavoritesFragment.adapters
        }
    }

    private fun initListener(){
        with(binding){
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getCountriesFavorites()
            }
        }
    }

    private fun setUpViewModel() {
        (activity as? BaseActivity)?.subscribeViewModelToFragment(viewModel, binding.root)
        viewModel.getCountriesFavorites()
    }

    private fun initObservers() {

        viewModel.onCountriesFavorites().observe(viewLifecycleOwner) {
            setUi(it)
        }

        viewModel.onLoading().observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.updateResult.observe(viewLifecycleOwner) { success ->
            when (success) {
                true -> {
                    viewModel.getCountriesFavorites()
                    Snackbar.make(
                        binding.root,
                        getString(R.string.succes_delete_favorite),
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                false -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.error_delete_favorito),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewModel.updateError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(
                    binding.root,
                    getString(R.string.error_delete_favorito),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setUi(country : List<CountryFavoriteEntity>) {
        adapters.submitList(country)
        showEmptyMessage(country.isEmpty())
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

    override fun onItemClicked(country: CountryFavoriteEntity?) {
        startDetailCountry(country?.commonName.toString())
    }

    override fun onItemDeleteClicked(country: CountryFavoriteEntity) {
        viewModel.deleteFavorite(country)
    }
}
