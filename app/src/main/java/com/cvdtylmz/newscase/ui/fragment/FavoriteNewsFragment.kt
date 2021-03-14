package com.cvdtylmz.newscase.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cvdtylmz.newscase.R
import com.cvdtylmz.newscase.base.BaseFragment
import com.cvdtylmz.newscase.common.NewsAppNavigator
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.databinding.FragmentFavoriteNewsBinding
import com.cvdtylmz.newscase.ui.adapter.FavoriteArticleAdapter
import com.cvdtylmz.newscase.util.viewBinding
import com.cvdtylmz.newscase.viewmodel.SharedNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_news.*

@AndroidEntryPoint
class FavoriteNewsFragment : BaseFragment<SharedNewsViewModel>(R.layout.fragment_favorite_news) {

    override val binding by viewBinding(FragmentFavoriteNewsBinding::bind)

    override val viewModel: SharedNewsViewModel by activityViewModels()

    private var list: List<Article> = listOf()

    private var favoriteArticleAdapter: FavoriteArticleAdapter? = null


    override fun observeViewModel(viewModel: SharedNewsViewModel) {
        super.observeViewModel(viewModel)
        viewModel.favoriteArticles.observe(viewLifecycleOwner) {
            Log.i("Query", "observeViewModel:${list.size} ")
            list = it
            favoriteArticleAdapter?.notifyDataSetChanged()

        }
    }

    override fun viewDidLoad(savedInstanceState: Bundle?) {
        super.viewDidLoad(savedInstanceState)
        viewModel.getFavoriteArticles()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        favoriteArticleAdapter = FavoriteArticleAdapter({
            viewModel.setSelectedArticle(it)
            Log.i("Query", "setRecyclerViewArticle:${viewModel.getSelectedArticle()}")
            NewsAppNavigator.navigateFragment(NewsDetailFragment())
        }, list)
        with(binding) {
            rvFavoriteNews.run {
                adapter = favoriteArticleAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        this@FavoriteNewsFragment.requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
                layoutManager = LinearLayoutManager(this@FavoriteNewsFragment.requireContext())
            }
        }
    }
}