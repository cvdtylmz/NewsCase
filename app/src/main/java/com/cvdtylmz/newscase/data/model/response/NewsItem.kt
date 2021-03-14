package com.cvdtylmz.newscase.data.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

data class NewsItem(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    var favorite: Boolean = false
) : Serializable

data class Source(
    val id: String,
    val name: String
)