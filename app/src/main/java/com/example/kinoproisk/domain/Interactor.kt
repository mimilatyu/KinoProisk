package com.example.kinoproisk.domain

import com.example.kinoproisk.data.Entity.TmdbResults
import com.example.kinoproisk.data.MainRepository
import com.example.kinoproisk.data.PreferenceProvider
import com.example.kinoproisk.data.TmdbApi
import com.example.kinoproisk.utils.Converter.convertApiListToDTOList
import com.example.kinoproisk.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page).enqueue(object: Callback<TmdbResults> {

            override fun onResponse(call: Call<TmdbResults>, response: Response<TmdbResults>) {
                val list = convertApiListToDTOList(response.body()?.tmdbFilms)
                list.forEach {
                    repo.putToDb(film = it)
                }
                callback.onSuccess(list)
            }

            override fun onFailure(call: Call<TmdbResults>, t: Throwable) {
                callback.onFailure()
            }
        })
    }



    fun saveDefaultCategoryToPrefences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): List<Film> = repo.getAllFromDB()

}