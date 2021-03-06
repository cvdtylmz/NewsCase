package com.cvdtylmz.newscase.di

import android.content.Context
import androidx.room.Room
import com.cvdtylmz.newscase.db.ArticleDao
import com.cvdtylmz.newscase.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ArticleDatabase {
        return Room
            .databaseBuilder(appContext, ArticleDatabase::class.java, "article_db.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(db: ArticleDatabase): ArticleDao {
        return db.getArticleDao()
    }
}