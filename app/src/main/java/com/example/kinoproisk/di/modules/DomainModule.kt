package com.example.kinoproisk.di.modules

import com.example.kinoproisk.data.MainRepository
import com.example.kinoproisk.data.TmdbApi
import com.example.kinoproisk.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}