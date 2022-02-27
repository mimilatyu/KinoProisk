package com.example.kinoproisk.di.modules

import android.content.Context
import androidx.room.Room
import com.example.kinoproisk.data.DAO.FilmDao
import com.example.kinoproisk.data.DatabaseHelper
import com.example.kinoproisk.data.Entity.AppDataBase
import com.example.kinoproisk.data.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context) =
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "film_db"
        ).build().filmDao()

    @Provides
    @Singleton
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
}