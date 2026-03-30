package com.luvsoft.countryapp.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.luvsoft.base.ui.activities.BaseActivity
import com.luvsoft.countryapp.databinding.ActivitySplashBinding
import com.luvsoft.countryapp.viewmodel.AndroidViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val viewModel: AndroidViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initView()
    }

    private fun initView() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeViewModelToFragment(viewModel, binding.root)

        binding.splashImage.alpha = 0f
        binding.splashImage.visibility = View.VISIBLE

        val fadeIn = ObjectAnimator.ofFloat(binding.splashImage, "alpha", 0f, 1f).apply {
            duration = 2000
        }
        fadeIn.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                openMainActivity()
            }
        })

        fadeIn.start()
    }

    private fun openMainActivity() {
        viewModel.startActivityList()
        viewModel.onCloseView()
    }
}