package com.cvdtylmz.newscase.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.data.repository.NewsRepositoryImpl
import com.cvdtylmz.newscase.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedNewsViewModel @ViewModelInject constructor(
    private val repository: NewsRepositoryImpl
) :
    ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Article>>? = null

    private val _selectedArticle = MutableLiveData<Article>()
    private val _selectedUrl = MutableLiveData<String?>()

    val favoriteArticles = MutableLiveData<List<Article>>()

    val insertedId = MutableLiveData<Long>()


    fun getFavoriteArticles() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getFavoriteArticles()
            }
            response.collect { articleList ->
                run {
                    favoriteArticles.value = articleList
                }
            }
        }
    }

    fun searchArticles(queryString: String): Flow<PagingData<Article>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult = repository.searchNews(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }


    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }

    fun insertArticle(article: Article) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.insertArticle(article)
            }
            insertedId.value = response

        }
    }

    fun setSelectedArticle(article: Article) {
        _selectedArticle.value = article
    }

    fun getSelectedArticle() = _selectedArticle

    fun setSourceUrl(url: String) {
        _selectedUrl.value = url
    }

    fun getSourceUrl() = _selectedUrl

}