package com.cvdtylmz.newscase.ui.adapter.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.cvdtylmz.newscase.data.model.response.Article

class ArticleDiffUtil : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean {
        return oldItem.publishedAt == newItem.publishedAt
    }

    override fun areContentsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean {
        return oldItem == newItem
    }
}