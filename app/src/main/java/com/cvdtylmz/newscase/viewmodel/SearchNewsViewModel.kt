package com.cvdtylmz.newscase.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNewsViewModel @ViewModelInject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Article>>? = null


    fun searchArticles(queryString: String): Flow<PagingData<Article>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult = newsRepository.searchNews(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }


}