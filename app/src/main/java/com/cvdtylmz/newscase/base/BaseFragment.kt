package com.cvdtylmz.newscase.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.cvdtylmz.newscase.ui.activity.MainActivity
import com.trendyol.medusalib.navigator.MultipleStackNavigator

abstract class BaseFragment<VM : ViewModel>(val view: Int) : Fragment(view) {

    abstract val binding: ViewBinding
    abstract val viewModel: VM
    private var multipleStackNavigator: MultipleStackNavigator? = null


    open fun observeViewModel(viewModel: VM) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initStackNavigator(context)
    }

    private fun initStackNavigator(context: Context?) {
        if (context is MainActivity && multipleStackNavigator == null) {
            multipleStackNavigator = context.multipleStackNavigator
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStackNavigator(context)
        viewDidLoad(savedInstanceState)
        observeViewModel(viewModel = viewModel)
    }

    open fun viewDidLoad(savedInstanceState: Bundle?) {

    }

}