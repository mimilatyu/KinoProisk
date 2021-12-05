package com.example.kinoproisk.domain

import com.example.kinoproisk.data.MainRepository

class Interactor(val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmsDataBase
}