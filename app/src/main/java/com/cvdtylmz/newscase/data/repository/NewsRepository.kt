package com.cvdtylmz.newscase.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cvdtylmz.newscase.data.apiservice.NewsApiService
import com.cvdtylmz.newscase.data.datasource.FavoriteNewsPagingSource
import com.cvdtylmz.newscase.data.datasource.NewsPagingDataSource
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NewsRepository {
    suspend fun insertArticle(article: Article) : Long
    suspend fun deleteArticle(article: Article)
    fun getFavoriteArticles(): Flow<List<Article>>
    fun searchNews(query: String): Flow<PagingData<Article>>
}

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val localDataSource: FavoriteNewsPagingSource
) :
    NewsRepository {

    override suspend fun insertArticle(article: Article) : Long {
        return localDataSource.insertArticle(article)
    }

    override suspend fun deleteArticle(article: Article) {
        localDataSource.deleteArticle(article)
    }

    override fun getFavoriteArticles(): Flow<List<Article>> {
        return localDataSource.getFavoriteNews()
    }

    override fun searchNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(10, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingDataSource(apiService, query) }
        ).flow
    }
}
