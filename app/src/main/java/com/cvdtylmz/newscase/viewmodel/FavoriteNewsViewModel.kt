package com.cvdtylmz.newscase.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteNewsViewModel @ViewModelInject constructor(private val repository: NewsRepository) :
    ViewModel() {

    val favoriteArticles = MutableLiveData<List<Article>>()


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
}