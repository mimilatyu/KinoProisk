package com.example.kinoproisk.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinoproisk.App
import com.example.kinoproisk.domain.Film
import com.example.kinoproisk.domain.Interactor

class HomeFragmentViewModel: ViewModel() {
    val filmsListLiveData = MutableLiveData<List<Film>>()
    private var interactor: Interactor = App.instance.interactor
    init{
        val films = interactor.getFilmsDB()
        filmsListLiveData.postValue(films)
    }
}