package com.cvdtylmz.newscase.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cvdtylmz.newscase.common.loadImageUrl
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.databinding.ItemNewsBinding
import com.cvdtylmz.newscase.util.viewBinding

class ArticleAdapter(private val callback: (Article?) -> Unit) :
    PagingDataAdapter<Article,
            ArticleAdapter.ArticleViewHolder>(CharactersDiffUtil()) {

    class ArticleViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Article?) {
            binding.txtNewsTitle.text = model?.title
            binding.txtNewsDescription.text = model?.description
            binding.imgNewsPhoto.loadImageUrl(url = model?.urlToImage, applyCircle = false)
        }

    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.root.setOnClickListener {
            callback.invoke(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(parent.viewBinding(ItemNewsBinding::inflate))
    }


}

class CharactersDiffUtil : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean {
        return oldItem == newItem
    }
}

