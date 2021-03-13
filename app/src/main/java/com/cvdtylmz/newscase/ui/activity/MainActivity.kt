package com.cvdtylmz.newscase.ui.activity

import com.cvdtylmz.newscase.util.HideBottomViewOnScrollBehavior
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.cvdtylmz.newscase.R
import com.cvdtylmz.newscase.common.AnimState
import com.cvdtylmz.newscase.common.AnimationOnChangeListener
import com.cvdtylmz.newscase.common.NewsAppNavigator
import com.cvdtylmz.newscase.databinding.ActivityMainBinding
import com.cvdtylmz.newscase.ui.fragment.FavoriteNewsFragment
import com.cvdtylmz.newscase.ui.fragment.NewsFragment
import com.cvdtylmz.newscase.util.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trendyol.medusalib.navigator.MultipleStackNavigator
import com.trendyol.medusalib.navigator.Navigator
import com.trendyol.medusalib.navigator.NavigatorConfiguration
import com.trendyol.medusalib.navigator.transaction.NavigatorTransaction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator.NavigatorListener {

    private val binding by viewBinding(ActivityMainBinding::inflate)


    private fun bottomNavBehaviourListener() {
        val params: CoordinatorLayout.LayoutParams =
            binding.bottomNav.layoutParams as CoordinatorLayout.LayoutParams
        (params.behavior as HideBottomViewOnScrollBehavior).apply {
            this.setListener(object : AnimationOnChangeListener {
                override fun animationChanged(state: AnimState) {
                    Log.d("***", state.toString())

                    when (state) {
                        AnimState.SLIDE_DOWN -> frameContainerLaySetMargin(0)
                        AnimState.SLIDE_UP -> frameContainerLaySetMargin(binding.bottomNav.height)
                    }
                }


            })

        }

    }

    private fun frameContainerLaySetMargin(bottomMargin: Int) {
        val params: CoordinatorLayout.LayoutParams =
            CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.MATCH_PARENT
            )
        params.setMargins(0, 0, 0, bottomMargin)
        binding.layFragmentContainer.layoutParams = params
    }

    private val rootFragmentProvider: List<() -> Fragment> = listOf(
        { NewsFragment() },
        { FavoriteNewsFragment() }
    )

    val multipleStackNavigator: MultipleStackNavigator =
        MultipleStackNavigator(
            supportFragmentManager,
            R.id.layFragmentContainer,
            rootFragmentProvider,
            navigatorListener = this,
            navigatorConfiguration = NavigatorConfiguration(
                0,
                true,
                NavigatorTransaction.ATTACH_DETACH
            )
        )

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_news -> {
                    multipleStackNavigator.switchTab(0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    multipleStackNavigator.switchTab(1)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    override fun onTabChanged(tabIndex: Int) {
        when (tabIndex) {
            0 -> binding.bottomNav.selectedItemId = R.id.navigation_news
            1 -> binding.bottomNav.selectedItemId = R.id.navigation_favorites
        }
    }

    override fun onResume() {
        NewsAppNavigator.register(multipleStackNavigator)
        Log.i("Navigator", "onResume:$multipleStackNavigator ")
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        multipleStackNavigator.initialize(savedInstanceState)
        NewsAppNavigator.register(multipleStackNavigator)
        bottomNavBehaviourListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        multipleStackNavigator.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }


    override fun onDestroy() {
        Log.i("Navigator", "onDestroy:$multipleStackNavigator ")
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        Log.i("Navigator", "onPauseWorked: ")
        if (isFinishing) {
            NewsAppNavigator.unRegister()
            Log.i("Navigator", "isFinishing:$multipleStackNavigator ")
        }
    }


    override fun onBackPressed() {
        if (multipleStackNavigator.canGoBack())
            multipleStackNavigator.goBack()
        else super.onBackPressed()
    }

}
