package com.cvdtylmz.newscase.data.datasource

import androidx.paging.PagingSource
import com.cvdtylmz.newscase.data.apiservice.ApiConstants
import com.cvdtylmz.newscase.data.apiservice.NewsApiService
import com.cvdtylmz.newscase.data.model.response.Article
import retrofit2.HttpException


class NewsPagingDataSource(private val apiService: NewsApiService, private val query: String) :
    PagingSource<Int, Article>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val page = params.key ?: 1
            val queryMap = hashMapOf<String, Any>(
                "q" to query,
                "page" to page,
                "apiKey" to ApiConstants.API_KEY
            )
            val repoResponse = apiService.getNews(queryMap)

            if (!repoResponse.isSuccessful || repoResponse.code() in 400..500) {
                return LoadResult.Error(HttpException(repoResponse))
            }
            val result = repoResponse.body()
            result?.let {
                return LoadResult.Page(
                    data = it.articles,
                    prevKey = if (page > 1) page - 1 else null,
                    nextKey = if (repoResponse.body()!!.articles.isEmpty()) null else page + 1
                )
            } ?: run {
                return LoadResult.Error(HttpException(repoResponse))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

}