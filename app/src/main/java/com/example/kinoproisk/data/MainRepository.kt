package com.example.kinoproisk.data

import androidx.lifecycle.LiveData
import com.example.kinoproisk.data.DAO.FilmDao
import com.example.kinoproisk.data.Entity.Film
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class MainRepository (private val filmDao: FilmDao) {
    fun putToDb(films: List<Film>) {
            filmDao.insertAll(films)
    }

    fun getAllFromDB(): Flow<List<Film>> = filmDao.getCachedFilms()
}