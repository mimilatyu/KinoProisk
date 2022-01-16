package com.example.kinoproisk

import android.app.Application
import com.example.kinoproisk.data.MainRepository
import com.example.kinoproisk.di.AppComponent
import com.example.kinoproisk.di.DaggerAppComponent
import com.example.kinoproisk.domain.Interactor



class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

       dagger = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}