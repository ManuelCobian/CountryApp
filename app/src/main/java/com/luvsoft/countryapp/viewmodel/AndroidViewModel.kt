package com.luvsoft.countryapp.viewmodel

import android.content.Intent
import com.luvsoft.base.viewmodels.BaseViewModel
import com.luvsoft.base.viewmodels.models.StartActivityModel
import com.luvsoft.countryapp.ui.activities.MainActivity

open class AndroidViewModel : BaseViewModel() {

    fun startActivityList() {
        startActivity.postValue(
            StartActivityModel(
                MainActivity::class.java,
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }
}