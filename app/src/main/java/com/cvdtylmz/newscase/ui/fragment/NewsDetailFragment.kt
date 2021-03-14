package com.cvdtylmz.newscase.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.cvdtylmz.newscase.R
import com.cvdtylmz.newscase.base.BaseFragment
import com.cvdtylmz.newscase.common.NewsAppNavigator
import com.cvdtylmz.newscase.common.dateFormat
import com.cvdtylmz.newscase.common.loadImage
import com.cvdtylmz.newscase.common.loadImageUrl
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.databinding.FragmentNewsDetailBinding
import com.cvdtylmz.newscase.util.viewBinding
import com.cvdtylmz.newscase.viewmodel.NewsDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<NewsDetailViewModel>(R.layout.fragment_news_detail) {

    override val binding by viewBinding(FragmentNewsDetailBinding::bind)

    override val viewModel: NewsDetailViewModel by viewModels()

    private var currentArticle: Article? = null

    override fun observeViewModel(viewModel: NewsDetailViewModel) {
        super.observeViewModel(viewModel)
        viewModel.insertedId.observe(viewLifecycleOwner) {
            currentArticle?.id = it
        }
    }


    override fun viewDidLoad(savedInstanceState: Bundle?) {
        super.viewDidLoad(savedInstanceState)
        getBundles()
        setToolbarListener()
        setBtnNewSourceListener()
    }

    private fun getBundles() {
        if (arguments != null) {
            currentArticle = arguments?.getSerializable("Article") as Article
            getSelectedNewAndBind()
        }
    }

    private fun setBtnNewSourceListener() {
        with(binding) {
            btnNavigateNewsSource.setOnClickListener {
                currentArticle?.url?.let { it1 ->
                    NewsAppNavigator.navigateWebViewFragment(
                        WebViewFragment(),
                        it1
                    )
                }
            }
        }
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
                currentArticle?.favorite = !currentArticle?.favorite!!
                if (currentArticle?.favorite == true) {
                    viewModel.insertArticle(currentArticle!!)
                    binding.toolbarNewsDetail.actionFav.loadImage(R.drawable.ic_baseline_favorite_24)

                } else {
                    viewModel.deleteArticle(currentArticle!!)
                    binding.toolbarNewsDetail.actionFav.loadImage(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }
    }

    private fun startShareIntent() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, currentArticle?.url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }


    private fun getSelectedNewAndBind() {
        binding.toolbarNewsDetail.actionFav.loadImage(
            if (currentArticle?.favorite == false) R.drawable.ic_baseline_favorite_border_24
            else R.drawable.ic_baseline_favorite_24
        )

        binding.imgNewsHeaderPhoto.loadImageUrl(
            url = currentArticle?.urlToImage,
            applyCircle = false
        )
        binding.txtAuthorName.text = currentArticle?.source?.name
        binding.txtNewsDate.text = currentArticle?.publishedAt?.dateFormat()
        binding.txtNewsDetailTitle.text = currentArticle?.title
        binding.txtNewsDetailContent.text = currentArticle?.content

    }
}