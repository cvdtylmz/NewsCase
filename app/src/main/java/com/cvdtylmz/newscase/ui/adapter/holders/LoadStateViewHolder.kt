package com.cvdtylmz.newscase.ui.adapter.holders

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.cvdtylmz.newscase.databinding.ItemBottomLoadStateBinding

class LoadStateViewHolder(val binding: ItemBottomLoadStateBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        binding.btnRetry.isVisible = loadState is LoadState.Error
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }
}