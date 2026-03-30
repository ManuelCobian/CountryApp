package com.luvsoft.base.ui.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.luvsoft.base.R
import com.luvsoft.base.viewmodels.BaseViewModel
import com.luvsoft.base.viewmodels.models.FinishActivityModel
import com.luvsoft.base.viewmodels.models.StartActionModel
import com.luvsoft.base.viewmodels.models.StartActivityModel
import com.luvsoft.base.views.Loader
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.luvsoft.base.network.NetworkMonitorProvider
import com.luvsoft.base.utils.ConnectionQuality
import com.luvsoft.base.utils.NetworkState
import kotlinx.coroutines.flow.collectLatest

open class BaseActivity : AppCompatActivity() {

    private var paused: Boolean = false

    private lateinit var loadingView: Loader

    private var snackbar: Snackbar? = null

    private val prefs by lazy {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }


    fun subscribeViewModelToFragment(viewModel: BaseViewModel, root: View) {
        viewModel.loaderState().observe(this) { showLoading(it) }
        viewModel.closeView().observe(this) { this.close(it) }
        viewModel.startAction().observe(this) { startAction(it) }
        viewModel.startActivity().observe(this) { startActivity(it) }
    }

    protected open fun showLoading(showing: Boolean) {
        initLoading()
        loadingView.setState(showing)
    }

    protected fun close(finishActivityModel: FinishActivityModel) {
        if (finishActivityModel.intent != null) {
            setResult(finishActivityModel.code, finishActivityModel.intent)
        } else {
            setResult(finishActivityModel.code)
        }
        finish()
    }

    protected fun startAction(startActionModel: StartActionModel) {
        val intent = Intent(startActionModel.action)
        startActionModel.uri?.let { intent.setData(it) }
        startActionModel.bundle?.let { intent.putExtras(it) }

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, startActionModel.code)
        }
    }

    protected fun startActivity(startActivityModel: StartActivityModel) {
        val intent = Intent(baseContext, startActivityModel.activity)
        startActivityModel.bundle?.let { intent.putExtras(it) }
        intent.flags = startActivityModel.flags
        startActivityForResult(intent, startActivityModel.code)
    }

    private fun initLoading() {
        if (!this::loadingView.isInitialized) {
            loadingView = Loader(this)
            this.addContentView(
                loadingView,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    protected fun setupActionBar(
        toolbar: Toolbar,
        showButtonBack: Boolean,
        title: String = "",
        icon: Int = R.drawable.ic_arrow_back_24
    ) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(icon)
            actionBar.setDisplayHomeAsUpEnabled(showButtonBack)
            actionBar.setDisplayShowHomeEnabled(showButtonBack)
            if (title.isNotEmpty()) actionBar.title = title
        }
    }


    protected fun setbackPressed(function: () -> Unit) {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    function.invoke()
                }
            }
        )
    }

    fun verifyInternetStatus(): Boolean {

        val monitor = NetworkMonitorProvider.monitor
        var isNotGood = false

        lifecycleScope.launchWhenStarted {
            monitor.state.collectLatest { state ->
                when (state) {
                    is NetworkState.Offline -> {
                        showBanner("Sin conexión. Algunas funciones podrían no funcionar.")
                        isNotGood = true
                    }

                    is NetworkState.Online -> {
                        when (state.quality) {
                            ConnectionQuality.GOOD -> {
                                hideBanner()
                            }

                            ConnectionQuality.SLOW -> {
                                showBanner(
                                    "Tu conexión está muy lenta. No eres tú, es tu internet que hoy anda con flojera 😅"
                                )
                                isNotGood = true
                            }
                        }
                    }
                }
            }
        }
        return isNotGood;
    }

    private fun showBanner(message: String) {
        val rootView: View = findViewById(android.R.id.content) ?: return

        if (snackbar?.isShown == true) {
            snackbar?.setText(message)
        } else {
            snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
            snackbar?.show()
        }
    }

    private fun hideBanner() {
        snackbar?.dismiss()
        snackbar = null
    }

    open fun loadFragment(fragment: Fragment, nav_host: Int) {
        supportFragmentManager.beginTransaction()
            .replace(nav_host, fragment)
            .addToBackStack(null)
            .commit()
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onStart() {
        super.onStart()
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onResume() {
        super.onResume()
    }
}