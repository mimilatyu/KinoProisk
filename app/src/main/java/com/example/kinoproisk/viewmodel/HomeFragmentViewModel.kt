package com.example.kinoproisk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinoproisk.App
import com.example.kinoproisk.data.Entity.Film
import com.example.kinoproisk.domain.Interactor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
   @Inject
   lateinit var interactor: Interactor
   val filmsListData: Flow<List<Film>>
   val showProgressBar: Channel<Boolean>

   init {
       App.instance.dagger.inject(this)
       showProgressBar = interactor.progressBarState
       filmsListData = interactor.getFilmsFromDB()
       getFilms()
   }

    fun getFilms() {
        interactor.getFilmsFromApi(1)
    }
}