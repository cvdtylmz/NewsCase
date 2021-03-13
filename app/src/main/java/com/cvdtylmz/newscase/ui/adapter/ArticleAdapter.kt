package com.cvdtylmz.newscase.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.databinding.ItemNewsBinding
import com.cvdtylmz.newscase.ui.adapter.diffutils.ArticleDiffUtil
import com.cvdtylmz.newscase.ui.adapter.holders.ArticleViewHolder
import com.cvdtylmz.newscase.util.viewBinding

class ArticleAdapter(private val callback: (Article?) -> Unit) :
    PagingDataAdapter<Article, ArticleViewHolder>(ArticleDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(parent.viewBinding(ItemNewsBinding::inflate))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.root.setOnClickListener {
            callback.invoke(getItem(position))
        }
    }
}



