package com.cvdtylmz.newscase.ui.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.cvdtylmz.newscase.databinding.ItemBottomLoadStateBinding
import com.cvdtylmz.newscase.ui.adapter.holders.LoadStateViewHolder
import com.cvdtylmz.newscase.util.viewBinding

class LoadMoreStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
        holder.binding.btnRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(parent.viewBinding(ItemBottomLoadStateBinding::inflate))
    }

}