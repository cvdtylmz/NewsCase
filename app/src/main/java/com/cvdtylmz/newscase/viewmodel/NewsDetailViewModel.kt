package com.cvdtylmz.newscase.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvdtylmz.newscase.data.model.response.Article
import com.cvdtylmz.newscase.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsDetailViewModel @ViewModelInject constructor(private val repository: NewsRepository) :
    ViewModel() {

    val insertedId = MutableLiveData<Long>()


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
}