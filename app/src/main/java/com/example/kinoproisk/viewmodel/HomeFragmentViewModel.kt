package com.example.kinoproisk.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kinoproisk.App
import com.example.kinoproisk.data.Entity.Film
import com.example.kinoproisk.domain.Interactor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
   @Inject
   lateinit var interactor: Interactor
   val filmsListData: Observable<List<Film>>
   val showProgressBar: BehaviorSubject<Boolean>

   init {
       App.instance.dagger.inject(this)
       showProgressBar = interactor.progressBarState
       filmsListData = interactor.getFilmsFromDB()
       getFilms()
   }

    fun getFilms() {
        interactor.getFilmsFromApi(1)
    }

    fun getSearchResult(search: String) = interactor.getSearchResultFromApi(search)
}