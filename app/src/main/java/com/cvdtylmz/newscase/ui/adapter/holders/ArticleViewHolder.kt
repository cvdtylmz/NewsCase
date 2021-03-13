package com.cvdtylmz.newscase.ui.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import com.cvdtylmz.newscase.common.loadImageUrl
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.databinding.ItemNewsBinding

class ArticleViewHolder(val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Article?) {
        binding.txtNewsTitle.text = model?.title
        binding.txtNewsDescription.text = model?.description
        binding.imgNewsPhoto.loadImageUrl(url = model?.urlToImage, applyCircle = false)
    }

}
