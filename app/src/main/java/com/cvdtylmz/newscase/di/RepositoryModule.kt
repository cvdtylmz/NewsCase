package com.cvdtylmz.newscase.di

import com.cvdtylmz.newscase.data.repository.NewsRepository
import com.cvdtylmz.newscase.data.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNewsRepository(repository: NewsRepositoryImpl): NewsRepository


}