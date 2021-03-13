package com.cvdtylmz.newscase.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import com.cvdtylmz.newscase.R
import com.cvdtylmz.newscase.base.BaseFragment
import com.cvdtylmz.newscase.databinding.FragmentWebviewBinding
import com.cvdtylmz.newscase.util.viewBinding
import com.cvdtylmz.newscase.viewmodel.SharedNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<SharedNewsViewModel>(R.layout.fragment_webview) {

    override val binding by viewBinding(FragmentWebviewBinding::bind)
    override val viewModel: SharedNewsViewModel by activityViewModels()

    override fun observeViewModel(viewModel: SharedNewsViewModel) {
        super.observeViewModel(viewModel)
    }

    override fun viewDidLoad(savedInstanceState: Bundle?) {
        super.viewDidLoad(savedInstanceState)
        setWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        with(binding) {
            webView.webViewClient = WebViewClient()
            webView.apply {
                viewModel.getSourceUrl().value?.let { loadUrl(it) }
                settings.domStorageEnabled = true
                settings.javaScriptEnabled = true
            }
        }
    }
}
