package com.example.kinoproisk.viewmodel

import android.net.NetworkInfo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainActivityViewModel: ViewModel() {
    val errorEvent = SingleLiveEvent<String>()

    fun postError() {
        errorEvent.postValue("NO CONNECTION")
    }


}