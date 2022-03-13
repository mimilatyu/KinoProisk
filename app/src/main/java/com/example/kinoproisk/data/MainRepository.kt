package com.example.kinoproisk.data

import com.example.kinoproisk.data.DAO.FilmDao
import com.example.kinoproisk.data.Entity.Film
import io.reactivex.rxjava3.core.Observable

class MainRepository (private val filmDao: FilmDao) {
    fun putToDb(films: List<Film>) {
            filmDao.insertAll(films)
    }

    fun getAllFromDB(): Observable<List<Film>> = filmDao.getCachedFilms()
}