package com.example.kinoproisk.domain

import androidx.lifecycle.LiveData
import com.example.kinoproisk.data.Entity.Film
import com.example.kinoproisk.data.Entity.TmdbResults
import com.example.kinoproisk.data.MainRepository
import com.example.kinoproisk.data.PreferenceProvider
import com.example.kinoproisk.data.TmdbApi
import com.example.kinoproisk.utils.Converter
import com.example.kinoproisk.utils.Converter.convertApiListToDTOList
import com.example.kinoproisk.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {


    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        progressBarState.onNext(true)
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .enqueue(object: Callback<TmdbResults>{

                override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                    val list = convertApiListToDTOList(response.body()?.tmdbFilms)
                    Completable.fromSingle<List<Film>> {
                        repo.putToDb(list)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                    progressBarState.onNext(false)

                }

                override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                    progressBarState.onNext(false)
                }
        })
    }



    fun saveDefaultCategoryToPrefences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()

}