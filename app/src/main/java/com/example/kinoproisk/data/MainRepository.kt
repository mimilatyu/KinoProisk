package com.example.kinoproisk.data

import androidx.lifecycle.LiveData
import com.example.kinoproisk.data.DAO.FilmDao
import com.example.kinoproisk.data.Entity.Film
import java.util.concurrent.Executors

class MainRepository (private val filmDao: FilmDao) {
    fun putToDb(films: List<Film>) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): LiveData<List<Film>> = filmDao.getCachedFilms()
}