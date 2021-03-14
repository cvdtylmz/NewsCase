package com.cvdtylmz.newscase.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cvdtylmz.newscase.data.model.response.Article
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

    fun currentFragment(): Fragment? {
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

    fun navigateWebViewFragment(T: Fragment, url: String) {
        navigator?.start(T.apply {
            arguments = Bundle().apply {
                putString("Url", url)
            }
        })
    }

    fun navigateFragmentWithArticle(T: Fragment, article: Article?) {
        navigator?.start(T.apply {
            arguments = Bundle().apply {
                putSerializable("Article", article)
            }
        })
    }
}