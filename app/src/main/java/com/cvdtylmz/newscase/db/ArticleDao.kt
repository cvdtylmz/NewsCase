package com.cvdtylmz.newscase.db

import androidx.room.*
import com.cvdtylmz.newscase.data.model.response.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article): Long

    @Query("SELECT * FROM articles ORDER BY id DESC ")
    fun getFavoriteArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}