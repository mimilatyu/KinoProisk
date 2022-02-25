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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {

    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    val progressBarState = Channel<Boolean>(Channel.CONFLATED)

    fun getFilmsFromApi(page: Int) {
        scope.launch {
            progressBarState.send(true)
        }

        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .enqueue(object: Callback<TmdbResults>{

                override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                    val list = Converter.convertApiListToDTOList(response.body()?.tmdbFilms)
                    scope.launch {
                        repo.putToDb(list)
                        progressBarState.send(false)
                    }
                }

                override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                    scope.launch {
                        progressBarState.send(false)
                    }
                }
        })
    }



    fun saveDefaultCategoryToPrefences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): Flow<List<Film>> = repo.getAllFromDB()

}