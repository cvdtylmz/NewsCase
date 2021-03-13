package com.cvdtylmz.newscase.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cvdtylmz.newscase.data.apiservice.NewsApiService
import com.cvdtylmz.newscase.data.datasource.NewsDataSource
import com.cvdtylmz.newscase.data.model.response.Article
import kotlinx.coroutines.flow.Flow

class SharedNewsViewModel @ViewModelInject constructor(private val apiService: NewsApiService) :
    ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Article>>? = null

    private val _selectedArticle = MutableLiveData<Article?>()
    private val _selectedUrl = MutableLiveData<String?>()

    fun searchArticles(queryString: String): Flow<PagingData<Article>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Article>> = Pager(
            config = PagingConfig(10, enablePlaceholders = false),
            pagingSourceFactory = { NewsDataSource(apiService, queryString) }
        ).flow.cachedIn(viewModelScope)
        currentSearchResult = newResult
        Log.i("Query", "searchArticles:$newResult.")
        return newResult
    }

    fun setSelectedArticle(article: Article?) {
        _selectedArticle.value = article
    }

    fun getSelectedArticle() = _selectedArticle

    fun setSourceUrl(url: String) {
        _selectedUrl.value = url
    }


    fun getSourceUrl() = _selectedUrl

}