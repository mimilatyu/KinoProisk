package com.example.kinoproisk.di.modules

import android.content.Context
import com.example.kinoproisk.data.MainRepository
import com.example.kinoproisk.data.PreferenceProvider

import com.example.kinoproisk.domain.Interactor
import com.example.remote_module.TmdbApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module

class DomainModule (val context: Context) {
    @Provides
    fun provideContext() = context

    @Singleton
    @Provides
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi, preferenceProvider: PreferenceProvider) =
        Interactor(repo = repository, retrofitService = tmdbApi, preferences = preferenceProvider)
}