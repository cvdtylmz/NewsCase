package com.cvdtylmz.newscase.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.databinding.ItemNewsBinding
import com.cvdtylmz.newscase.ui.adapter.holders.FavoriteArticleViewHolder
import com.cvdtylmz.newscase.util.viewBinding

class FavoriteArticleAdapter(
    private val callback: (Article) -> Unit,
    private val dataList: List<Article>
) :
    RecyclerView.Adapter<FavoriteArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteArticleViewHolder {
        return FavoriteArticleViewHolder(parent.viewBinding(ItemNewsBinding::inflate))
    }

    override fun onBindViewHolder(holder: FavoriteArticleViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            callback.invoke(dataList[holder.absoluteAdapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}