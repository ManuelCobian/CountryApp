package com.luvsoft.countryapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.luvsoft.base.ui.activities.BaseActivity
import com.luvsoft.countryapp.R
import com.luvsoft.countryapp.databinding.ActivityMainBinding
import com.luvsoft.countryapp.ui.fragments.CountryFragment
import com.luvsoft.countryapp.ui.fragments.FavoritesFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import dagger.hilt.android.AndroidEntryPoint

const val HOME_ITEM = 1
const val FAVORITES_ITEM = 2

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        drawBottom()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
        applyEdgeToEdgeInsets()
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }


    private fun applyEdgeToEdgeInsets() {
        val fragmentContainer: View = findViewById(R.id.fragmentContainer)
        val bottomNavigation: View = findViewById(R.id.bottomNavigation)
        ViewCompat.setOnApplyWindowInsetsListener(fragmentContainer) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = systemBars.top,
                bottom = systemBars.bottom
            )
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigation) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updatePadding(
                bottom = systemBars.bottom          // que no quede pegado al borde de gestos
            )

            insets
        }
    }

    private fun drawBottom() {
        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(
            CurvedBottomNavigation.Model(
                HOME_ITEM,
                resources.getString(com.luvsoft.base.R.string.txt_menu_home),
                com.luvsoft.base.R.drawable.ic_home
            ),
        )

        bottomNavigation.add(
            CurvedBottomNavigation.Model(
                FAVORITES_ITEM,
                resources.getString(com.luvsoft.base.R.string.txt_menu_favorites),
                com.luvsoft.base.R.drawable.ic_favorite
            ),
        )

        startManagerFragment(bottomNavigation)
    }

    private fun startManagerFragment(bottomNavigation: CurvedBottomNavigation) {
        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                HOME_ITEM -> {
                    loadFragment(CountryFragment(), R.id.fragmentContainer)
                }

                FAVORITES_ITEM -> {
                    loadFragment(FavoritesFragment(), R.id.fragmentContainer)
                }

            }
        }
        loadFragment(CountryFragment(), R.id.fragmentContainer)
        bottomNavigation.show(HOME_ITEM)
    }
}