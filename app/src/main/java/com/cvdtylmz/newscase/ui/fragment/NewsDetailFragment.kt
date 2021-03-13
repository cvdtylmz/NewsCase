package com.cvdtylmz.newscase.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.cvdtylmz.newscase.R
import com.cvdtylmz.newscase.base.BaseFragment
import com.cvdtylmz.newscase.common.NewsAppNavigator
import com.cvdtylmz.newscase.common.dateFormat
import com.cvdtylmz.newscase.common.loadImageUrl
import com.cvdtylmz.newscase.databinding.FragmentNewsDetailBinding
import com.cvdtylmz.newscase.util.viewBinding
import com.cvdtylmz.newscase.viewmodel.SharedNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<SharedNewsViewModel>(R.layout.fragment_news_detail) {

    override val binding by viewBinding(FragmentNewsDetailBinding::bind)

    override val viewModel: SharedNewsViewModel by activityViewModels()


    override fun viewDidLoad(savedInstanceState: Bundle?) {
        super.viewDidLoad(savedInstanceState)
        getSelectedNewAndBind()
        setToolbarListener()
    }

    private fun setToolbarListener() {
        with(binding) {
            toolbarNewsDetail.actionBack.setOnClickListener {
                activity?.onBackPressed()
            }
            toolbarNewsDetail.actionShare.setOnClickListener {
                startShareIntent()
            }
            toolbarNewsDetail.actionFav.setOnClickListener {
                // todo --> add favorites
            }
        }
    }

    private fun startShareIntent() {
        val urlValue = viewModel.getSelectedArticle().value?.url
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlValue)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun getSelectedNewAndBind() {
        val article = viewModel.getSelectedArticle()
        with(binding) {
            btnNavigateNewsSource.setOnClickListener {
                article.value?.url?.let { it1 -> viewModel.setSourceUrl(it1) }
                NewsAppNavigator.navigateFragment(WebViewFragment())
            }
            imgNewsHeaderPhoto.loadImageUrl(url = article.value?.urlToImage, applyCircle = false)
            txtAuthorName.text = article.value?.source?.name
            txtNewsDate.text = article.value?.publishedAt?.dateFormat()
            txtNewsDetailTitle.text = article.value?.title
            txtNewsDetailContent.text = article.value?.content

        }
    }
}