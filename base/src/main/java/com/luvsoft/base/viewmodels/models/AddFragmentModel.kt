package com.luvsoft.base.viewmodels.models

import com.luvsoft.base.ui.fragments.BaseFragment

class AddFragmentModel(
    val fragment: BaseFragment,
    val container: Int,
    val addToStack: Boolean = false,
    val bundleParams: Map<String, Any>? = null,
    val popBackStackParam: Int? = null
) {

    constructor(
        fragmentClassName: Class<out BaseFragment>,
        container: Int,
        bundleParams: Map<String, Any>? = null,
        addToStack: Boolean = true,
        popBackStackParam: Int? = null
    ) : this(
        fragmentClassName.newInstance(),
        container = container,
        bundleParams = bundleParams,
        addToStack = addToStack,
        popBackStackParam = popBackStackParam
    )
}
