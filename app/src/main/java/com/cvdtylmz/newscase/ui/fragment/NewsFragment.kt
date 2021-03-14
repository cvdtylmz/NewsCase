package com.cvdtylmz.newscase.ui.fragment

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cvdtylmz.newscase.R
import com.cvdtylmz.newscase.base.BaseFragment
import com.cvdtylmz.newscase.common.NewsAppNavigator
import com.cvdtylmz.newscase.databinding.FragmentNewsBinding
import com.cvdtylmz.newscase.ui.adapter.ArticleAdapter
import com.cvdtylmz.newscase.ui.adapter.LoadMoreStateAdapter
import com.cvdtylmz.newscase.util.viewBinding
import com.cvdtylmz.newscase.viewmodel.SharedNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<SharedNewsViewModel>(R.layout.fragment_news) {

    override val binding by viewBinding(FragmentNewsBinding::bind)
    override val viewModel: SharedNewsViewModel by activityViewModels()


    private val articleAdapter = ArticleAdapter {
        viewModel.setSelectedArticle(it!!)
        NewsAppNavigator.navigateFragment(NewsDetailFragment())
    }

    private fun setSearchView() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    query?.let {
                        viewModel.searchArticles(it).collectLatest { data ->
                            articleAdapter.submitData(data)
                        }
                        Log.i("Query", "setSearchView:${binding.searchBar.query.toString()} ")
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }

    override fun viewDidLoad(savedInstanceState: Bundle?) {
        super.viewDidLoad(savedInstanceState)
        setSearchView()
        setRecyclerView()
        setAdapterLoadState()
    }

    private fun setRecyclerView () {
        binding.rvNews.run {
            adapter = articleAdapter.withLoadStateFooter(
                footer = LoadMoreStateAdapter {
                    articleAdapter.retry()
                }
            )
            addItemDecoration(
                DividerItemDecoration(
                    this@NewsFragment.requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(this@NewsFragment.requireContext())
        }
    }

    private fun setAdapterLoadState() {
        articleAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                lifecycleScope.launchWhenResumed {
                    binding.progressBar.isVisible = true
                }

            } else {
                lifecycleScope.launchWhenResumed {
                    binding.progressBar.isGone = true
                }

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    makeText(context, it.error.message, LENGTH_SHORT).show()
                }

            }
        }

    }
}