package com.cvdtylmz.newscase.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.cvdtylmz.newscase.R
import com.cvdtylmz.newscase.base.BaseFragment
import com.cvdtylmz.newscase.common.NewsAppNavigator
import com.cvdtylmz.newscase.common.dateFormat
import com.cvdtylmz.newscase.common.loadImage
import com.cvdtylmz.newscase.common.loadImageUrl
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.databinding.FragmentNewsDetailBinding
import com.cvdtylmz.newscase.util.viewBinding
import com.cvdtylmz.newscase.viewmodel.SharedNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<SharedNewsViewModel>(R.layout.fragment_news_detail) {

    override val binding by viewBinding(FragmentNewsDetailBinding::bind)

    override val viewModel: SharedNewsViewModel by activityViewModels()

    private var currentArticle: Article? = null

    override fun observeViewModel(viewModel: SharedNewsViewModel) {
        super.observeViewModel(viewModel)
        viewModel.insertedId.observe(viewLifecycleOwner) {
            currentArticle?.id = it
        }
    }


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
        currentArticle = viewModel.getSelectedArticle().value
        binding.toolbarNewsDetail.actionFav.loadImage(
            if (currentArticle?.favorite == false) R.drawable.ic_baseline_favorite_border_24
            else R.drawable.ic_baseline_favorite_24
        )
        with(binding) {
            btnNavigateNewsSource.setOnClickListener {
                currentArticle?.url?.let { it1 -> viewModel.setSourceUrl(it1) }
                NewsAppNavigator.navigateFragment(WebViewFragment())
            }
            imgNewsHeaderPhoto.loadImageUrl(url = currentArticle?.urlToImage, applyCircle = false)
            txtAuthorName.text = currentArticle?.source?.name
            txtNewsDate.text = currentArticle?.publishedAt?.dateFormat()
            txtNewsDetailTitle.text = currentArticle?.title
            txtNewsDetailContent.text = currentArticle?.content

            favoriteChecker(currentArticle)
        }
    }

    private fun favoriteChecker(favoriteArticle: Article?) {
        binding.toolbarNewsDetail.actionFav.setOnClickListener {
            favoriteArticle.let { data ->
                data!!.favorite = !data.favorite
                if (data.favorite) {
                    viewModel.insertArticle(data)
                    binding.toolbarNewsDetail.actionFav.loadImage(R.drawable.ic_baseline_favorite_24)

                } else {
                    viewModel.deleteArticle(data)
                    binding.toolbarNewsDetail.actionFav.loadImage(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }
    }
}