package com.cvdtylmz.newscase.common

import androidx.fragment.app.Fragment
import com.trendyol.medusalib.navigator.MultipleStackNavigator

object NewsAppNavigator {

    private var navigator: MultipleStackNavigator? = null

    fun register(navigator: MultipleStackNavigator?) {
        this.navigator = navigator
    }

    fun unRegister() {
        navigator = null
    }

    fun popBack() {
        navigator?.safeBack()
    }

    fun currentFragment () : Fragment? {
        return navigator?.getCurrentFragment()
    }

    fun backToRoot(tabIndex: Int) {
        if (navigator?.hasOnlyRoot(tabIndex) == false) navigator?.reset(tabIndex)
    }

    private fun MultipleStackNavigator.safeBack() {
        if (this.canGoBack()) this.goBack()
    }

    fun navigateFragment(T: Fragment) {
        navigator?.start(
            T.apply {
            }
        )
    }
}