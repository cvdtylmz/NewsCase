package com.cvdtylmz.newscase.data.datasource

import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.db.ArticleDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteNewsPagingSource @Inject constructor(private val articleDao: ArticleDao) {

    fun getFavoriteNews(): Flow<List<Article>> {
        return articleDao.getFavoriteArticles()
    }

    suspend fun deleteArticle(article: Article) {
        articleDao.deleteArticle(article)
    }

    suspend fun insertArticle(article: Article): Long {
        return articleDao.insertArticle(article)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }


}