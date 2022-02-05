package com.example.kinoproisk.data

import com.example.kinoproisk.data.DAO.FilmDao
import com.example.kinoproisk.data.Entity.Film
import java.util.concurrent.Executors

class MainRepository (private val filmDao: FilmDao) {
    fun putToDb(films: List<Film>) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): List<Film> {
        return filmDao.getCachedFilms()
    }
}