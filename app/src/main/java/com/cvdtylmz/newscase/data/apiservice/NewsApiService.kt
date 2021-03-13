package com.cvdtylmz.newscase.data.apiservice

import com.cvdtylmz.newscase.data.model.response.NewsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApiService {

    @GET("v2/everything")
    suspend fun getNews(@QueryMap mapValues: Map<String, @JvmSuppressWildcards Any>): Response<NewsItem>
}