package com.cvdtylmz.newscase.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.cvdtylmz.newscase.R
import com.cvdtylmz.newscase.base.BaseFragment
import com.cvdtylmz.newscase.databinding.FragmentWebviewBinding
import com.cvdtylmz.newscase.util.viewBinding
import com.cvdtylmz.newscase.viewmodel.WebViewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<WebViewViewModel>(R.layout.fragment_webview) {

    override val binding by viewBinding(FragmentWebviewBinding::bind)
    override val viewModel: WebViewViewModel by viewModels()

    override fun viewDidLoad(savedInstanceState: Bundle?) {
        super.viewDidLoad(savedInstanceState)
        setWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        with(binding) {
            webView.webViewClient = WebViewClient()
            webView.apply {
                arguments?.getString("Url", null)?.let { loadUrl(it) }
                settings.domStorageEnabled = true
                settings.javaScriptEnabled = true
            }
        }
    }
}
