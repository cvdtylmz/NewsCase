package com.cvdtylmz.newscase.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.cvdtylmz.newscase.R
import com.cvdtylmz.newscase.base.BaseFragment
import com.cvdtylmz.newscase.databinding.FragmentFavoriteNewsBinding
import com.cvdtylmz.newscase.util.viewBinding
import com.cvdtylmz.newscase.viewmodel.FavoriteNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteNewsFragment : BaseFragment<FavoriteNewsViewModel>(R.layout.fragment_favorite_news) {

    override val binding: ViewBinding by viewBinding(FragmentFavoriteNewsBinding::bind)

    override val viewModel: FavoriteNewsViewModel by viewModels()

    override fun observeViewModel(viewModel: FavoriteNewsViewModel) {
        super.observeViewModel(viewModel)
    }

    override fun viewDidLoad(savedInstanceState: Bundle?) {
        super.viewDidLoad(savedInstanceState)
    }
}